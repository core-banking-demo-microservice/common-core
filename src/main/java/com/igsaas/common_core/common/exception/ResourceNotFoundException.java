package com.igsaas.common_core.common.exception;


import com.igsaas.common_core.common.exception.aabstract.AbstractResourceNotFoundException;

public class ResourceNotFoundException extends AbstractResourceNotFoundException {

    public ResourceNotFoundException(final String resourceName, final long identifier) {
        super("error.msg.resource.not.found", String.format("Resource [%s] with identifier [%s] not found", resourceName, identifier));
    }

    public ResourceNotFoundException(final String message) {
        super("error.msg.resource.not.found", message);
    }
}