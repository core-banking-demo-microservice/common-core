package com.igsaas.common_core.common.exception.filter;

import com.google.gson.Gson;
import com.igsaas.common_core.common.dto.ApiParameterError;
import com.igsaas.common_core.common.dto.ErrorResponse;
import com.igsaas.common_core.common.dto.HttpException;
import com.igsaas.common_core.common.exception.DataIntegrityValidationException;
import com.igsaas.common_core.common.exception.aabstract.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class GlobalExceptionFilter {
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Object> handleWebClientResponseException(final WebClientResponseException ex) {
        final var error = new HashMap<String, Object>();
        final var errorResponse = new Gson().fromJson(ex.getResponseBodyAsString(), HttpException.class);
        error.put("errorCode", errorResponse.errorCode());
        error.put("errorMessage", errorResponse.errorMessage());
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(ex.getStatusCode().value()));
    }

    @ExceptionHandler(AbstractUnauthorizedException.class)
    public final ResponseEntity<Object> handelUnAuthorizeOtpException(final AbstractUnauthorizedException exception) {
        final var error = new HashMap<String, Object>();
        error.put("errorCode", exception.getErrorCode());
        error.put("errorMessage", exception.getErrorMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AbstractServiceUnavailableException.class)
    public final ResponseEntity<Object> handelServiceUnavailableException(final AbstractServiceUnavailableException exception) {
        final var error = new HashMap<String, Object>();
        error.put("errorCode", exception.getErrorCode());
        error.put("errorMessage", exception.getErrorMessage());
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(AbstractForbiddenException.class)
    public final ResponseEntity<Object> handelForbiddenExceptionException(final AbstractForbiddenException exception) {
        final var error = new HashMap<String, Object>();
        error.put("errorCode", exception.getErrorCode());
        error.put("errorMessage", exception.getErrorMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AbstractBadRequestException.class)
    public final ResponseEntity<Object> handelForbiddenExceptionException(final AbstractBadRequestException exception) {
        final var error = new HashMap<String, Object>();
        error.put("errorCode", exception.getErrorCode());
        error.put("errorMessage", exception.getErrorMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AbstractDataValidationException.class)
    public final ResponseEntity<Object> handelDataValidationException(final AbstractDataValidationException exception) {
        final var error = new HashMap<String, Object>();
        error.put("errorCode", exception.getErrorCode());
        error.put("errorMessage", exception.getErrorMessage());
        error.put("fieldErrors", exception.getErrors());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AbstractResourceNotFoundException.class)
    public final ResponseEntity<Object> handelResourceNotFoundException(final AbstractResourceNotFoundException exception) {
        final var error = new HashMap<String, Object>();
        error.put("errorCode", exception.getErrorCode());
        error.put("errorMessage", exception.getErrorMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AbstractExternalServiceException.class)
    public final ResponseEntity<Object> handelExternalServiceException(final AbstractExternalServiceException exception) {
        final var error = new HashMap<String, Object>();
        error.put("errorCode", exception.getErrorCode());
        error.put("errorMessage", exception.getErrorMessage());
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(AbstractInternalServerErrorException.class)
    public final ResponseEntity<Object> handelInternalServerErrorException(final AbstractInternalServerErrorException exception) {
        final var error = new HashMap<String, Object>();
        error.put("errorCode", exception.getErrorCode());
        error.put("errorMessage", "Internal Server Error.");
        log.error("e: ", exception);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handelException(final Exception exception) {
        final var error = new HashMap<String, Object>();
        error.put("errorCode", "error.msg.internal.server.error");
        error.put("errorMessage", "Internal Server Error.");
        log.error("e: ", exception);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AbstractResourceConflictException.class)
    public ResponseEntity<Object> handleResourceConflictException(final AbstractResourceConflictException ex) {
        final var error = new HashMap<String, Object>();
        error.put("errorCode", ex.getErrorCode());
        error.put("errorPath", ex.getErrorPath());
        error.put("errorMessage", ex.getErrorMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AbstractGeneralDomainRuleException.class)
    public ResponseEntity<Object> handleAbstractGeneralDomainRuleException(final AbstractGeneralDomainRuleException ex) {
        final var error = new HashMap<String, Object>();
        error.put("errorCode", ex.getErrorCode());
        error.put("errorMessage", ex.getErrorMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AbstractDataIntegrityValidationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(final AbstractDataIntegrityValidationException ex) {
        final var errors = new ArrayList<ApiParameterError>();
        final var pattern = Pattern.compile("(?<=ERROR:)([a-zA-Z\\s\"\\_:\\(\\)=0-9]+).*[.]");
        final var matcher = pattern.matcher(ex.getMessage());
        var msg = ex.getMessage();
        if (matcher.find()) {
            msg = matcher.group().trim();
            msg = msg.replaceAll("(?=[A-Za-z0-9\\s]) (\\\"[A-Za-z0-9_\\\"]+\n)", ".");
            msg = msg.replace("(lower(", "").replace("::text))", "");
        }
        errors.add(ApiParameterError.generalError("error.msg.resource.conflict.list", msg));
        final var dataValidationException = new DataIntegrityValidationException(errors);
        final var errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), dataValidationException.getErrorCode(), dataValidationException.getMessage());
        errorResponse.setErrors(dataValidationException.getErrors());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        List<Map<String, String>> fieldErrors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("field", fieldError.getField());
            errorDetails.put("errorMessage", fieldError.getDefaultMessage());
            fieldErrors.add(errorDetails);
        });

        errorResponse.put("errorCode", "VALIDATION_ERROR");
        errorResponse.put("errorMessage", "Validation failed for one or more fields.");
        errorResponse.put("fieldErrors", fieldErrors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(WebExchangeBindException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        List<Map<String, String>> fieldErrors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("field", fieldError.getField());
            errorDetails.put("errorMessage", fieldError.getDefaultMessage());
            fieldErrors.add(errorDetails);
        });

        errorResponse.put("errorCode", "VALIDATION_ERROR");
        errorResponse.put("errorMessage", "Validation failed for one or more fields.");
        errorResponse.put("fieldErrors", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
