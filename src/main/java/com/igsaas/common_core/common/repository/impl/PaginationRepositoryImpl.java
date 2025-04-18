package com.igsaas.common_core.common.repository.impl;

import com.igsaas.common_core.common.repository.AuditEntity;
import com.igsaas.common_core.common.repository.BaseRepositoryService;
import com.igsaas.common_core.common.repository.PaginationRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public class PaginationRepositoryImpl<T extends AuditEntity> implements PaginationRepository<T> {
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final Class<T> entityClass;

    public PaginationRepositoryImpl(R2dbcEntityTemplate r2dbcEntityTemplate, Class<T> entityClass) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
        this.entityClass = entityClass;
    }

    protected Set<String> getSortableColumns() {
        return Set.of("created_date", "id", "created_by");
    }

    protected Criteria getBaseCriteria() {
        return Criteria.empty();
    }

    public Flux<T> findAllPagination(final Pageable pageable) {
        return r2dbcEntityTemplate.select(BaseRepositoryService.buildPageableQuery(getBaseCriteria(), pageable, getSortableColumns()),
                entityClass);
    }

    public Mono<Long> finaAllCount() {
        return r2dbcEntityTemplate.select(Query.query(getBaseCriteria()),
                entityClass).count();
    }
}
