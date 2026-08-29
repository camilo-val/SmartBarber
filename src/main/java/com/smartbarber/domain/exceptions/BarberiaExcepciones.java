package com.smartbarber.domain.exceptions;

import lombok.Getter;

@Getter
public class BarberiaExcepciones extends RuntimeException{

    private final MensajesExcepcionBarberia mensajesExcepcionBarberia;
    public BarberiaExcepciones(MensajesExcepcionBarberia mensajesExcepcionBarberia) {
        super(mensajesExcepcionBarberia.getMensaje());
        this.mensajesExcepcionBarberia = mensajesExcepcionBarberia;
    }
}
