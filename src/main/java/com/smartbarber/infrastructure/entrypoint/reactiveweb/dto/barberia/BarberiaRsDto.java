package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barberia;

import com.smartbarber.domain.enums.TipoDocumento;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record BarberiaRsDto (
        UUID id,
        String nombre,
        String descripcion,
        String ubicacion,
        String celular,
        String documento,
        TipoDocumento tipoDocumento,
        String razonSocial,
        String estado,
        LocalDate fechaCreacion,
        LocalDate fechaModificacion
){

}
