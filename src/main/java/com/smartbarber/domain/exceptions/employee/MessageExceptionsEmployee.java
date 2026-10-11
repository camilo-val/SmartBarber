package com.smartbarber.domain.exceptions.employee;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum MessageExceptionsEmployee implements ErrorMessage {

    INVALID_EMPLOYEE("EY_001", "invalid employee"),
    EMPLOYEE_ALREADY_EXISTS("CT_002", "employee already exists"),
    INVALID_DATA("CT_003","invalid data"),
    EMPLOYEE_NOT_FOUND("CT_004", "employee not found");

    private final String code;
    private final String messgae;

    MessageExceptionsEmployee(String code, String messgae) {
        this.code = code;
        this.messgae = messgae;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return messgae;
    }
}
