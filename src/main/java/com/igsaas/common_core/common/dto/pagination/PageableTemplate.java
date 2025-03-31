package com.igsaas.common_core.common.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
public class PageableTemplate {
    private int pageNumber;
    private int pageSize;
    private Pageable pageable;
}
