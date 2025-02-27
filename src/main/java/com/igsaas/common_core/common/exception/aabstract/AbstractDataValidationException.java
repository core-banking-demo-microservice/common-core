package com.igsaas.common_core.common.exception.aabstract;

import com.igsaas.common_core.common.dto.DataValidationErrors;
import lombok.Getter;

import java.util.List;

@Getter
public class AbstractDataValidationException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;
    private final List<DataValidationErrors> errors;

    public AbstractDataValidationException(final String errorCode,
                                           final String errorMessage,
                                           final List<DataValidationErrors> errors) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errors = errors;
    }
}
