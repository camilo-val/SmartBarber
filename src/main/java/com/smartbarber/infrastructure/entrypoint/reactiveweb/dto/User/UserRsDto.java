package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record UserRsDto (
        UUID id,
        String firebaseId,
        String estado,
        LocalDate fechaCreacion,
        LocalDate fechaModificacion
){
}
