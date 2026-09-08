package com.smartbarber.domain.exceptions;

import lombok.Getter;

@Getter
public class ClientExceptions extends RuntimeException{

    private final MessageExceptionsClient messageExceptionsClient;
    public ClientExceptions(MessageExceptionsClient messageExceptionsClient){
        super(messageExceptionsClient.getMensaje());
        this.messageExceptionsClient = messageExceptionsClient;
    }
}
