package com.igsaas.common_core.common.exception.aabstract;

import com.igsaas.common_core.common.dto.ApiParameterError;
import lombok.Getter;

import java.util.List;

@Getter
public class AbstractDataIntegrityValidationException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;
    private final List<ApiParameterError> errors;

    public AbstractDataIntegrityValidationException(final String errorCode,
                                                    final String errorMessage,
                                                    final List<ApiParameterError> errors) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errors = errors;
    }
}
