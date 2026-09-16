package com.smartbarber.domain.exceptions;

public enum MessageExceptionsEmployee implements ErrorMessage{

    EMPLOYEE_INVALIDO("EY_001", "invalid employee"),
    EMPLOYEE_EXISTENTE("CT_002", "employee already exists"),
    DATOS_INVALIDOS("CT_003","invalid data"),
    EMPLOYEE_NO_EXISTE("CT_004", "employee not found");

    private final String codigo;
    private final String mensaje;

    MessageExceptionsEmployee(String codigo, String mensaje) {
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
