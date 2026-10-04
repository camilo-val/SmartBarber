package com.smartbarber.domain.exceptions.authorization;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum AuthorizationMessageExceptions implements ErrorMessage {
    THE_TOKEN_IS_EXPIRED("AT_001", "the token has expired");

    private final String code;
    private final String message;


    AuthorizationMessageExceptions(String code, String message) {
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
