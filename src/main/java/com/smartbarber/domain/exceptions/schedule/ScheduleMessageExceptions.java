package com.smartbarber.domain.exceptions.schedule;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum ScheduleMessageExceptions implements ErrorMessage{

    SCHEDULE_INVALID("SH_001", "invalid schedule"),
    SCHEDULE_ALREADY_EXISTS("SH_002", "schedule already exists"),
    INVALID_DATA("SH_003","invalid data"),
    SCHEDULE_NOT_FOUND("SH_004", "schedule not found");

    private final String codigo;
    private final String mensaje;

    ScheduleMessageExceptions(String codigo, String mensaje){
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    @Override
    public String getCode(){return codigo;}

    @Override
    public String getMessage(){return mensaje;}
}
