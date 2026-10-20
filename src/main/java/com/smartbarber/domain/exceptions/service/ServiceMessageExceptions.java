package com.smartbarber.domain.exceptions.service;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum ServiceMessageExceptions implements ErrorMessage {

    SERVICE_INVALID("SC_001", "invalid service"),
    SERVICE_ALREADY_EXISTS("SC_002", "service already exists"),
    INVALID_DATA("SC_003", "invalid data"),
    SERVICE_NOT_FOUND("SC_004", "service not found");

    private final String code;
    private final String message;

    ServiceMessageExceptions(String code, String message) {
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