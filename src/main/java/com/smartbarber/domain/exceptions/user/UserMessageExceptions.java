package com.smartbarber.domain.exceptions.user;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum UserMessageExceptions implements ErrorMessage {
    USUARIO_INVALIDO("US_001","Usuario Invalido"),
    USUARIO_EXISTENTE("US_002","Usuario Existente"),
    DATOS_INVALIDOS("US_003","Datos Invalidos"),
    USUARIO_NO_EXISTE("US_004","Usuario no existe");

    private final String codigo;
    private final String mensaje;

    UserMessageExceptions(String codigo, String mensaje){
        this.codigo = codigo;
        this.mensaje = mensaje;
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
