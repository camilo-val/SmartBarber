package com.smartbarber.domain.exceptions;

public enum MessageExceptionUser {
    USUARIO_INVALIDO("US_001","Usuario Invalido"),
    USUARIO_EXISTENTE("US_002","Usuario Existente"),
    DATOS_INVALIDOS("US_003","Datos Invalidos"),
    USUARIO_NO_EXISTE("US_004","Usuario no existe");

    private final String codigo;
    private final String mensaje;

    MessageExceptionUser (String codigo, String mensaje){
        this.codigo = codigo;
        this.mensaje = mensaje;
    }
    public String getCodigo() { return codigo; }
    public String getMensaje() { return mensaje; }
}
