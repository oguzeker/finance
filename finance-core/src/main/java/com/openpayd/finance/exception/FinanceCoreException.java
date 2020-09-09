package com.openpayd.finance.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FinanceCoreException extends RuntimeException {

    private FinanceCoreError error = FinanceCoreError.INTERNAL_SERVER_ERROR;
    private Object[] args;
    private String message;

    public FinanceCoreException(FinanceCoreError error, Object... args) {
        this.error = error;
        this.args = args;
    }

    public FinanceCoreException(FinanceCoreError error, Throwable cause, Object... args) {
        super(cause);
        this.error = error;
        this.args = args;
    }

}