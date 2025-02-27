package com.igsaas.common_core.common.exception;


import com.igsaas.common_core.common.exception.aabstract.AbstractForbiddenException;

public class ForbiddenException extends AbstractForbiddenException {
    public ForbiddenException(String msg) {
        super("error.msg.access.forbidden", "Access Denied: ".concat(msg));
    }

    public ForbiddenException() {
        super("error.msg.access.forbidden", "Access Denied: You do not have permission to access this resource.");
    }
}