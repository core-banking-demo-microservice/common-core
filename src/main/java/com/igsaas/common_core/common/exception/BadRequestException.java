package com.igsaas.common_core.common.exception;


import com.igsaas.common_core.common.exception.aabstract.AbstractBadRequestException;

public class BadRequestException extends AbstractBadRequestException {

    public BadRequestException(String msg) {
        super("error.msg.bad.request", msg);
    }
}
