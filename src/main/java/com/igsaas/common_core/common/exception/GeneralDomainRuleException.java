package com.igsaas.common_core.common.exception;


import com.igsaas.common_core.common.exception.aabstract.AbstractGeneralDomainRuleException;

public class GeneralDomainRuleException extends AbstractGeneralDomainRuleException {

    public GeneralDomainRuleException(final String message) {
        super("error.msg.general.domain.rule", message);
    }
}