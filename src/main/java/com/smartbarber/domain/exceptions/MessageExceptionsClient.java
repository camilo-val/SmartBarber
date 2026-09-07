package com.smartbarber.domain.exceptions;

public enum MessageExceptionsClient {

    CLIENT_INVALIDO("CT_001", "Cliente invalido"),
    CLIENT_EXISTENTE("CT_002", "Cliente existente"),
    DATOS_INVALIDOS("CT_003","datos invalidos"),
    CLIENT_NO_EXISTE("CT_004", "Cliente no existe");

    private final String codigo;
    private final String mensaje;

    MessageExceptionsClient(String codigo, String mensaje){
        this.codigo = codigo;
        this.mensaje= mensaje;
    }

    public String getCodigo() { return codigo; }
    public String getMensaje() { return mensaje; }
}
