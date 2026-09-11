package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee;

import com.smartbarber.domain.enums.TipoDocumento;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record EmployeeRsDto (
        UUID id,
        UUID userId,
        UUID barberiaId,
        String documento,
        TipoDocumento tipoDocumento,
        String nombre,
        String celular,
        String correo,
        String especialidad,
        LocalDate fechaCreacion,
        LocalDate fechaModificacion
){
}
