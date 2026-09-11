package com.smartbarber.domain.exceptions;

import lombok.Getter;

@Getter
public class EmployeeExceptions extends RuntimeException{

    private final MessageExceptionsEmployee messageExceptionsEmployee;
    public EmployeeExceptions(MessageExceptionsEmployee messageExceptionsEmployee){
        super(messageExceptionsEmployee.getMessage());
        this.messageExceptionsEmployee = messageExceptionsEmployee;
    }
}
