package com.igsaas.common_core.common.exception;

import com.igsaas.common_core.common.dto.DataValidationErrors;
import com.igsaas.common_core.common.exception.aabstract.AbstractDataValidationException;
import lombok.Getter;

import java.util.List;

@Getter
public class DataValidationException extends AbstractDataValidationException {
    public DataValidationException(List<DataValidationErrors> errors) {
        super("error.msg.data.validation.error", "Data Validation Error", errors);
    }
}
