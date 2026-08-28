package com.smartbarber.domain.exceptions;

public enum MensajesExcepcionBarberia {
    BARBERIA_INVALIDA("BA_001","Barberia invalida"),
    BARBERIA_EXISTENTE("BP_002","Barberia existente"),
    DATOS_INVALIDOS("BP_003","datos invalidos"),
    BARBERIA_NO_EXISTE("BP_004","Barberia no existente");

    private final String codigo;
    private final String mensaje;

    MensajesExcepcionBarberia(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }
    public String getCodigo() {
        return codigo;
    }
    public String getMensaje() {
        return mensaje;
    }
}
