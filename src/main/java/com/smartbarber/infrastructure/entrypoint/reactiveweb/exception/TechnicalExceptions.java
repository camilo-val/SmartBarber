package com.smartbarber.infrastructure.entrypoint.reactiveweb.exception;

import lombok.Getter;

@Getter
public class TechnicalExceptions extends RuntimeException{
    private final TechnicalMessageExceptions mensajesExcepciones;

    public TechnicalExceptions(TechnicalMessageExceptions mensajesExcepciones) {
        super(mensajesExcepciones.getMensaje());
        this.mensajesExcepciones = mensajesExcepciones;
    }
}
