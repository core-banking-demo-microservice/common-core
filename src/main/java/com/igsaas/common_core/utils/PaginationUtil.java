package com.igsaas.common_core.utils;

import com.igsaas.common_core.common.dto.pagination.PageableTemplate;
import com.igsaas.common_core.common.dto.pagination.PaginationRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PaginationUtil {
    public static PageableTemplate getPageable(PaginationRequest pagination) {
        final var pageNumber = Math.max(pagination.getPageNumber() - 1, 0);
        final var pageSize = pagination.getPageSize();
        final var sort = Sort.by(pagination.getSortDirection(), pagination.getSortBy().getFirst());
        final var pageable = PageRequest.of(pageNumber, pageSize, sort);
        return new PageableTemplate(pageNumber, pageSize, pageable);
    }

    public static String convertToSnakeCase(String input) {
        return input.replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase().replaceAll("date", "at");
    }
}