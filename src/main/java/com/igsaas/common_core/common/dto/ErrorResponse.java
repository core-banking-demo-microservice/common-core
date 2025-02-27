package com.igsaas.common_core.common.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {

    private Integer statusCode;
    private String errorCode;
    private String message;
    private List<ApiParameterError> errors;

    public ErrorResponse(final Integer statusCode,
                         final String errorCode,
                         final String message) {

        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }
}

