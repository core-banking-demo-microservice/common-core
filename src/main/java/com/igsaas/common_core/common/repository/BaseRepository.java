package com.igsaas.common_core.common.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@NoRepositoryBean
public interface BaseRepository<T extends AuditEntity, ID>
        extends ReactiveCrudRepository<T, ID>,
        SoftDeleteRepository<T, ID> {
}