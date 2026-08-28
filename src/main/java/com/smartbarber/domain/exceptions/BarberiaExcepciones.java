package com.smartbarber.domain.exceptions;

public class BarberiaExcepciones extends RuntimeException{

    private final MensajesExcepcionBarberia mensajesExcepcionBarberia;
    public BarberiaExcepciones(MensajesExcepcionBarberia mensajesExcepcionBarberia) {
        super(mensajesExcepcionBarberia.getMensaje());
        this.mensajesExcepcionBarberia = mensajesExcepcionBarberia;
    }
}
