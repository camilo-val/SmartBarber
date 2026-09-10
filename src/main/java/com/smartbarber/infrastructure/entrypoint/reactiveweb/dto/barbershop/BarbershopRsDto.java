package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barbershop;

import com.smartbarber.domain.enums.DocumentType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record BarbershopRsDto(
        UUID id,
        String nombre,
        String descripcion,
        String ubicacion,
        String celular,
        String documento,
        DocumentType tipoDocumento,
        String razonSocial,
        String estado,
        LocalDate fechaCreacion,
        LocalDate fechaModificacion
){

}
