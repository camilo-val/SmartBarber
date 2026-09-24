package com.smartbarber.domain.exceptions.transaction;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum TransactionMessageExceptions implements ErrorMessage {
    INVALID_STATUS("TS_001","invalid status for transaction"),
    TRANSACTION_ALREADY_EXISTS("TS_002","transaction already exists"),
    TRANSACTION_NOT_EXISTS("TS_003","transaction does not exist"),
    INVALID_TRANSACTION("TS_004","invalid data for transaction"),
    UNSUPPORTED_TRANSACTION_TYPE("TS_005","unsupported transaction type");

    private final String code;
    private final String message;

    TransactionMessageExceptions(String code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
