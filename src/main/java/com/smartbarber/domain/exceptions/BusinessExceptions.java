package com.smartbarber.domain.exceptions;


import lombok.Getter;

@Getter
public class BusinessExceptions extends RuntimeException {
    private final ErrorMessage exceptionMessage;

    public BusinessExceptions(ErrorMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
        this.exceptionMessage = exceptionMessage;
    }

}
