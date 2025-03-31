package com.igsaas.common_core.common.dto.pagination;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public class ReactiveSerializationUtils {

    public static <T> Mono<ResponseEntity<PaginatedResponse<T>>> paginateResponse(
            Mono<List<T>> dataMono,
            Mono<Long> totalCountMono,
            int pageNumber,
            int pageSize) {

        return Mono.zip(dataMono, totalCountMono)
                .map(tuple -> {
                    List<T> data = tuple.getT1();
                    long totalCount = tuple.getT2();
                    int totalPage = (int) Math.ceil((double) totalCount / pageSize);

                    final var pagination = new PaginatedResponse.Pagination(pageNumber + 1, pageSize, totalPage, totalCount);

                    PaginatedResponse<T> response = new PaginatedResponse<>();
                    response.setCode(200);
                    response.setMessage("Fetched Data Successfully");
                    response.setData(data);
                    response.setPagination(pagination);

                    return ResponseEntity.ok(response);
                });
    }
}

