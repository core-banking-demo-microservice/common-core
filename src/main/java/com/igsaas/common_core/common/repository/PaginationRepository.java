package com.igsaas.common_core.common.repository;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaginationRepository<T extends AuditEntity> {
    Flux<T> findAllPagination(final Pageable pageable);

    Mono<Long> finaAllCount();
}
