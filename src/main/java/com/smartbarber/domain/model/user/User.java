package com.smartbarber.domain.model.user;

import com.smartbarber.domain.exceptions.UserExceptions;
import com.smartbarber.domain.exceptions.MessageExceptionUser;

import java.net.PortUnreachableException;
import java.time.LocalDate;
import java.util.UUID;

public class User {
    private final UUID id;
    private final String firebaseId;
    private final String estado;
    private final LocalDate fechaCreacion;
    private final LocalDate fechaModificacion;

    private User(UUID id, String firebaseId, String estado, LocalDate fechaCreacion, LocalDate fechaModificacion){
        this.id = id;
        this.firebaseId = firebaseId;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public static User crear(UUID id , String firebaseId){
        return new User(
                id,
                firebaseId,
                "Activo",
                LocalDate.now(),
                null
        );
    }

    public static User actualizar(UUID id, String firebaseId, String estado, LocalDate fechaCreacion, LocalDate fechaModificacion){
        if (id == null) {
            throw new UserExceptions(
                    MessageExceptionUser.DATOS_INVALIDOS
            );
        }
        return new User(
                id,
                firebaseId,
                estado,
                fechaCreacion,
                fechaModificacion
        );
    }

    public static User reconstruir(UUID id, String firebaseId, String estado, LocalDate fechaCreacion, LocalDate fechaModificacion){
        if (id == null || estado == null || estado.isBlank()){
            throw new UserExceptions(MessageExceptionUser.DATOS_INVALIDOS);
        }
        return new User(
                id,
                firebaseId,
                estado,
                fechaCreacion,
                fechaModificacion
        );
    }

    private static boolean esNuloOBlanco(String texto){ return texto == null || texto.isBlank(); }

    public UUID getId() { return id; }

    public String getFirebaseId() { return firebaseId; }

    public String getEstado() {
        return estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }
}

