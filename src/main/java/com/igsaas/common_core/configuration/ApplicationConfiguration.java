package com.igsaas.common_core.configuration;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.igsaas.common_core.common.repository.impl.SoftDeleteRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;

public class ApplicationConfiguration {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .setDateFormat(new StdDateFormat())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .registerModules(new JavaTimeModule());

    @Bean
    public SoftDeleteRepositoryImpl<?, ?> softDeleteRepositoryImpl(
            R2dbcEntityOperations entityOperations,
            R2dbcConverter converter) {
        return new SoftDeleteRepositoryImpl<>(entityOperations, converter);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
        return mapper;
    }
}
