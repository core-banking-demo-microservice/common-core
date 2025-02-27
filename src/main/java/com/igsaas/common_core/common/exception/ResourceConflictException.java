package com.igsaas.common_core.common.exception;


import com.igsaas.common_core.common.exception.aabstract.AbstractResourceConflictException;

public class ResourceConflictException extends AbstractResourceConflictException {
    public ResourceConflictException(final String path, final String msg) {
        super("error.msg.resource.conflict", path, msg);
    }
}