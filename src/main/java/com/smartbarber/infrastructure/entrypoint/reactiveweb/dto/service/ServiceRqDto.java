package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ServiceRqDto (
        @NotNull(message = "El id de la barberia es obligatorio")
        UUID barberiaId,

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "La descripcion es obligatorio")
        String description,

        @NotNull(message = "La duracion es obligatoria")
        Integer duration,

        @NotNull(message = "El precio es obligatoria")
        Integer price,

        @NotNull(message = "Debe indicar si el servicio tiene oferta")
        Boolean offert,

        Integer special_price
){}
