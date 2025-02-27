package com.igsaas.common_core.common.exception;


import com.igsaas.common_core.common.exception.aabstract.AbstractUnauthorizedException;

public class UnauthorizedException extends AbstractUnauthorizedException {

    public UnauthorizedException() {
        super("error.msg.unauthorized", "Unauthorized: You need to log in to access this resource.");
    }

    public UnauthorizedException(final String message) {
        super("error.msg.unauthorized", message);
    }
}