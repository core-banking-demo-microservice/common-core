package com.igsaas.common_core.common.exception.aabstract;

import lombok.Getter;

@Getter
public class AbstractResourceConflictException extends RuntimeException {

    private final String errorPath;
    private final String errorCode;
    private final String errorMessage;

    public AbstractResourceConflictException(final String errorCode,
                                             final String errorPath,
                                             final String errorMessage) {
        super(errorMessage);
        this.errorPath = errorPath;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}