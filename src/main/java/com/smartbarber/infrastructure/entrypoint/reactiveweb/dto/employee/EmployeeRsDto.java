package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee;

import com.smartbarber.domain.enums.DocumentType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record EmployeeRsDto (
        UUID id,
        UUID userId,
        UUID barberiaId,
        String documento,
        DocumentType tipoDocumento,
        String nombre,
        String celular,
        String correo,
        String especialidad,
        LocalDate fechaCreacion,
        LocalDate fechaModificacion
){
}
