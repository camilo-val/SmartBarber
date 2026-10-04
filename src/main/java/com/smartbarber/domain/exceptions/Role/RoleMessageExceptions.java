package com.smartbarber.domain.exceptions.Role;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum RoleMessageExceptions implements ErrorMessage{
    INVALID_ROLE("RL_001", "invalid role"),
    ROLE_ALREADY_EXIST("RL_002", "role already exist"),
    INVALID_DATA("RL_003","invalid data"),
    ROLE_NOT_FOUND("RL_004", "role not found");

    private final String code;
    private final String message;

    RoleMessageExceptions(String code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {return code;}

    @Override
    public String getMessage() {return message;}
}
