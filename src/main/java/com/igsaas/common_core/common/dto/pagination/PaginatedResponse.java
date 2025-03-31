package com.igsaas.common_core.common.dto.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse<T> {
    private int code;
    private String message;
    private List<T> data;
    private Pagination pagination;

    @Data
    public static class Pagination {
        private int page;
        private int size;
        private int offSet;
        @JsonProperty("total_page")
        private int totalPage;
        @JsonProperty("total_counts")
        private long totalCounts;

        public Pagination(int page, int size, int totalPage, long totalCounts) {
            this.page = page;
            this.size = size;
            this.offSet = (page - 1) * size;
            this.totalPage = totalPage;
            this.totalCounts = totalCounts;
        }

        public Pagination() {
        }
    }
}
