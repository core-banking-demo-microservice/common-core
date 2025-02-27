package com.igsaas.common_core.log;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Slf4j
public class ReactiveLoggingFilter implements WebFilter {
    private static final List<String> SHOULD_NOT_FILTER_PATHS = List.of(
            "/api/v1/actuator/**",
            "/actuator"
    );
    private final List<String> keysToMask = List.of("password");

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, @NonNull final WebFilterChain chain) {
        final var request = exchange.getRequest();
        final var path = request.getPath().value();
        if (SHOULD_NOT_FILTER_PATHS.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        final var start = Instant.now();
        final var method = request.getMethod().toString();
        final var response = exchange.getResponse();
        final var clientIp = getClientIpAddress(request);
        final var isMultipart = MediaType.MULTIPART_FORM_DATA.isCompatibleWith(request.getHeaders().getContentType());

        if (isMultipart) {
            return chain.filter(exchange).doOnTerminate(() -> {
                final var end = Instant.now();
                final var timeTaken = Duration.between(start, end);
                final var responseHttpStatus = response.getStatusCode();

                log.info("clientIP: {}, method: {}, URI: {}, httpCode: {}, requestBody: {}, spendTime: {} ms",
                        clientIp,
                        method,
                        path,
                        responseHttpStatus,
                        "",
                        timeTaken.toMillis());
            });
        }

        return request.getBody()
                .collectList()
                .flatMap(buffers -> {
                    final var body = buffers.stream()
                            .map(buffer -> StandardCharsets.UTF_8.decode(buffer.asByteBuffer()).toString())
                            .reduce("", String::concat);

                    final String regex = "(?<=(" + String.join("|", keysToMask) + ")=)[^&]+";

                    final var maskedBody = body.replaceAll(regex, "******");

                    final var dataBufferFactory = exchange.getResponse().bufferFactory();

                    final var mutatedRequest = new ServerHttpRequestDecorator(request) {
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return Flux.just(dataBufferFactory.wrap(body.getBytes(StandardCharsets.UTF_8)));
                        }
                    };

                    final var mutatedExchange = exchange.mutate().request(mutatedRequest).build();

                    // Continue the filter chain
                    return chain.filter(mutatedExchange)
                            .doOnTerminate(() -> {
                                final var end = Instant.now();
                                final var timeTaken = Duration.between(start, end);
                                final var responseHttpStatus = response.getStatusCode();

                                log.info("clientIP: {}, method: {}, URI: {}, httpCode: {}, requestBody: {}, spendTime: {} ms",
                                        clientIp,
                                        method,
                                        path,
                                        responseHttpStatus,
                                        maskedBody.replaceAll("\\s+", ""),
                                        timeTaken.toMillis());

                            });
                });
    }

    private String getClientIpAddress(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddress() != null ? request.getRemoteAddress().getAddress().getHostAddress() : "Unknown";
        }
        return ip;
    }
}
