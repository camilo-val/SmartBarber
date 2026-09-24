package com.smartbarber.infrastructure.entrypoint.reactiveweb.exception;

import lombok.Getter;

@Getter
public class TechnicalExceptions extends RuntimeException{
    private final TechnicalMessageExceptions mensajesExcepciones;

    public TechnicalExceptions(TechnicalMessageExceptions mensajesExcepciones) {
        super(mensajesExcepciones.getMessage());
        this.mensajesExcepciones = mensajesExcepciones;
    }
}
