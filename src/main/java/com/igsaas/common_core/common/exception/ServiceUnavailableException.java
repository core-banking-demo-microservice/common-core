package com.igsaas.common_core.common.exception;


import com.igsaas.common_core.common.exception.aabstract.AbstractServiceUnavailableException;

public class ServiceUnavailableException extends AbstractServiceUnavailableException {

    public ServiceUnavailableException() {
        super("error.msg.service.unavailable", "Service Unavailable: Service is not available");
    }

    public ServiceUnavailableException(final String message) {
        super("error.msg.service.unavailable", message);
    }
}