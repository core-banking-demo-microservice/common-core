package com.igsaas.common_core.common.exception;


import com.igsaas.common_core.common.exception.aabstract.AbstractInternalServerErrorException;

public class InternalServerErrorException extends AbstractInternalServerErrorException {

    public InternalServerErrorException() {
        super("error.msg.internal.server.error", "Internal Server Error.");
    }
}