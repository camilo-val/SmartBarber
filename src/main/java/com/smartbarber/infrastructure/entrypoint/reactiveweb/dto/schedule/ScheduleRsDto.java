package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.schedule;

import lombok.Builder;

import java.time.LocalTime;
import java.util.UUID;

@Builder
public record ScheduleRsDto (
        Integer id,
        UUID empleadoId,
        LocalTime startTime,
        LocalTime endTime,
        LocalTime lunchStartTime,
        LocalTime lunchEndTime,
        Boolean vacation
){
}
