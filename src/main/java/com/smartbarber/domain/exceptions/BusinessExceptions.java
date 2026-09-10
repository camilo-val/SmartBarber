package com.smartbarber.domain.exceptions;


public class BusinessExceptions extends RuntimeException {
    private final ErrorMessage exceptionMessage;

    public BusinessExceptions(ErrorMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
        this.exceptionMessage = exceptionMessage;
    }

    public ErrorMessage getExceptionMessage() {
        return exceptionMessage;
    }
}
