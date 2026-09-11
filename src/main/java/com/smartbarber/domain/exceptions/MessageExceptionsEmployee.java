package com.smartbarber.domain.exceptions;

public enum MessageExceptionsEmployee {

    EMPLOYEE_INVALIDO("EY_001", "Empleado invalido"),
    EMPLOYEE_EXISTENTE("CT_002", "Empleado existente"),
    DATOS_INVALIDOS("CT_003","datos invalidos"),
    EMPLOYEE_NO_EXISTE("CT_004", "Empleado no existe");

    private final String codigo;
    private final String mensaje;

    MessageExceptionsEmployee(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public String getCodigo() { return codigo; }
    public String getMensaje() { return mensaje; }
}
