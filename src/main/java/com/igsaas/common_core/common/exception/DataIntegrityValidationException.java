package com.igsaas.common_core.common.exception;

import com.igsaas.common_core.common.dto.ApiParameterError;
import com.igsaas.common_core.common.exception.aabstract.AbstractDataIntegrityValidationException;
import lombok.Getter;

import java.util.List;

@Getter
public class DataIntegrityValidationException extends AbstractDataIntegrityValidationException {

    public DataIntegrityValidationException(List<ApiParameterError> errors) {
        super("Validation exist", "error.msg.validation.exist", errors);
    }

}
