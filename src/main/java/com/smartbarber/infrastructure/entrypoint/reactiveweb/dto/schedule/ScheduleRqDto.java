package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalTime;
import java.util.UUID;

@Builder
public record ScheduleRqDto (
        @NotNull(message = "El empleado es obligatorio")
        UUID empleadoId,

        LocalTime startTime,

        LocalTime endTime,

        LocalTime lunchStartTime,

        LocalTime lunchEndTime,

        @NotNull(message = "Debe indicar si el empleado está de vacaciones")
        Boolean vacation
){
}
