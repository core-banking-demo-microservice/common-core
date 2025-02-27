package com.igsaas.common_core.common.exception;


import com.igsaas.common_core.common.exception.aabstract.AbstractExternalServiceException;

public class ExternalServiceException extends AbstractExternalServiceException {

    public ExternalServiceException(final String errorCode,
                                    final String errorMessage) {
        super(errorCode, errorMessage);
    }
}
