package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.client;

import com.smartbarber.domain.enums.TipoDocumento;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ClientRsDto (
        UUID id,
        UUID userId,
        String documento,
        TipoDocumento tipoDocumento,
        String nombre,
        String celular,
        String correo,
        LocalDate fechaCreacion,
        LocalDate fechaModificacion
){
}
