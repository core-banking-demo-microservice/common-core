package com.igsaas.common_core.aspects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "resilience4j.circuitbreaker.configs.default")
public class CircuitBreakerProperty {
    @JsonProperty("record-exceptions")
    private Set<String> recordExceptions;
}
