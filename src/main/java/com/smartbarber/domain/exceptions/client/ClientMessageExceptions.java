package com.smartbarber.domain.exceptions.client;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum ClientMessageExceptions implements ErrorMessage {

    CLIENT_INVALIDO("CT_001", "invalid client"),
    CLIENT_EXISTENTE("CT_002", "client already exists"),
    DATOS_INVALIDOS("CT_003","invalid data"),
    CLIENT_NO_EXISTE("CT_004", "client not found");

    private final String codigo;
    private final String mensaje;

    ClientMessageExceptions(String codigo, String mensaje){
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
