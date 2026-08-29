package com.smartbarber.infrastructure.entrypoint.reactiveweb.exception;

import lombok.Getter;

@Getter
public class ExcepcionesTecnicas extends RuntimeException{
    private final MensajesExcepcionesTecnicas mensajesExcepciones;

    public ExcepcionesTecnicas(MensajesExcepcionesTecnicas mensajesExcepciones) {
        super(mensajesExcepciones.getMensaje());
        this.mensajesExcepciones = mensajesExcepciones;
    }
}
