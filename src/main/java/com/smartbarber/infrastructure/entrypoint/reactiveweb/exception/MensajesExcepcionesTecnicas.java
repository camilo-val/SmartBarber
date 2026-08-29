package com.smartbarber.infrastructure.entrypoint.reactiveweb.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MensajesExcepcionesTecnicas {
    BAD_REQUEST("TECH_001","bad request"),
    TIME_OUT("TECH_002","time out"),
    SERVICE_UNAVAILABLE("TECH_003","service unavailable"),
    INTERNAL_SERVER_ERROR("TECH_004","internal server error"),
    UNEXPECTED_ERROR("TECH_005","unexpected error"),
    ;

    private final String code;
    private final String mensaje;
}
