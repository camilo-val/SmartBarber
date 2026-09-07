package com.smartbarber.domain.exceptions;

import lombok.Getter;

@Getter

public class UserExceptions extends RuntimeException {

    private final MessageExceptionUser messageExceptionUser;
    public UserExceptions(MessageExceptionUser messageExceptionUser){
        super(messageExceptionUser.getMensaje());
        this.messageExceptionUser = messageExceptionUser;
    }
}
