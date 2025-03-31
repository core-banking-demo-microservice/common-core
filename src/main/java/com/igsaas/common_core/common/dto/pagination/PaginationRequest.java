package com.igsaas.common_core.common.dto.pagination;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

@Setter
@Getter
public class PaginationRequest {
    private String keyword;
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    private List<String> sortBy = List.of("id");
}
