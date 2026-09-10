package com.smartbarber.domain.exceptions.client;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum ClientMessageExceptions implements ErrorMessage {

    CLIENT_INVALIDO("CT_001", "Cliente invalido"),
    CLIENT_EXISTENTE("CT_002", "Cliente existente"),
    DATOS_INVALIDOS("CT_003","datos invalidos"),
    CLIENT_NO_EXISTE("CT_004", "Cliente no existe");

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
