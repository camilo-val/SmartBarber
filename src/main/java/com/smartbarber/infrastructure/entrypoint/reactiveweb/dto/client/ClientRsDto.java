package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.client;

import com.smartbarber.domain.enums.DocumentType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ClientRsDto (
        UUID id,
        UUID userId,
        String documento,
        DocumentType tipoDocumento,
        String nombre,
        String celular,
        String correo,
        LocalDate fechaCreacion,
        LocalDate fechaModificacion
){
}
