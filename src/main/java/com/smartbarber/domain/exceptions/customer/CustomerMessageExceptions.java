package com.smartbarber.domain.exceptions.customer;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum CustomerMessageExceptions implements ErrorMessage {

    INVALID_CUSTOMER("CT_001", "invalid client"),
    CUSTOMER_ALREADY_EXISTS("CT_002", "client already exists"),
    INVALID_DATA("CT_003","invalid data"),
    CUSTOMER_NOT_FOUD("CT_004", "client not found");

    private final String codigo;
    private final String mensaje;

    CustomerMessageExceptions(String codigo, String mensaje){
        this.codigo = codigo;
        this.mensaje= mensaje;
    }

    @Override
    public String getCode() {
        return codigo;
    }

    @Override
    public String getMessage() {
        return mensaje;
    }
}
