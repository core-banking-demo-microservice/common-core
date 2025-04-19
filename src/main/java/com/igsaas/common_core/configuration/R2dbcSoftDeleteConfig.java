package com.igsaas.common_core.configuration;

import com.igsaas.common_core.common.repository.impl.SoftDeleteRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories(repositoryBaseClass = SoftDeleteRepositoryImpl.class)
public class R2dbcSoftDeleteConfig {
}