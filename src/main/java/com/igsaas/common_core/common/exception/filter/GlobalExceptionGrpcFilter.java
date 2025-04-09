package com.igsaas.common_core.common.exception.filter;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

public class GlobalExceptionGrpcFilter extends GlobalExceptionFilter {
    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<Object> handleGrpcStatusRuntimeException(final StatusRuntimeException ex) {
        final var error = new HashMap<String, Object>();
        error.put("errorCode", ex.getStatus().getCode().name());
        error.put("errorPath", "gRPC");
        error.put("errorMessage", ex.getStatus().getDescription());

        HttpStatus httpStatus = mapGrpcStatusToHttp(ex.getStatus().getCode());
        return new ResponseEntity<>(error, httpStatus);
    }

    private HttpStatus mapGrpcStatusToHttp(Status.Code grpcCode) {
        return switch (grpcCode) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INVALID_ARGUMENT -> HttpStatus.BAD_REQUEST;
            case ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case UNAUTHENTICATED -> HttpStatus.UNAUTHORIZED;
            case PERMISSION_DENIED -> HttpStatus.FORBIDDEN;
            case UNAVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
            case INTERNAL -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.BAD_GATEWAY;
        };
    }
}
