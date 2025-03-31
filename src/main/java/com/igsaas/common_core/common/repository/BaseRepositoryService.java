package com.igsaas.common_core.common.repository;

import com.igsaas.common_core.utils.PaginationUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;

import java.util.Set;
import java.util.stream.Collectors;

public class BaseRepositoryService {
    public static Sort buildSort(final Pageable pageable, final Set<String> sortableColumns) {
        return Sort.by(
                pageable.getSort().stream()
                        .filter(order -> sortableColumns.contains(PaginationUtil.convertToSnakeCase(order.getProperty())))
                        .map(order -> new Sort.Order(order.getDirection(), PaginationUtil.convertToSnakeCase(order.getProperty())))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Builds a pageable query based on the provided criteria and pagination information.
     * <p>
     * This method constructs a query with sorting, pagination limits, and offsets.
     * It ensures that the sorting columns are validated against the provided set of sortable columns.
     * <p>
     * Note: Sortable columns should be matched with database fields in snake case format (created_at).
     *
     * @param criteria        the criteria to filter the results
     * @param pageable        the pagination information, including page size and offset
     * @param sortableColumns a set of columns that are allowed for sorting, should match snake_case database fields
     * @return a {@link Query} object configured with the specified criteria, sorting, and pagination settings
     */
    public static Query buildPageableQuery(final Criteria criteria, final Pageable pageable, final Set<String> sortableColumns) {
        final var validatedSort = buildSort(pageable, sortableColumns);
        return Query.query(criteria)
                .sort(validatedSort)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
    }
}
