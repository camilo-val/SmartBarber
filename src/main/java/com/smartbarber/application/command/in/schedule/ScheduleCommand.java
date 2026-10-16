package com.smartbarber.application.command.in.schedule;

import lombok.Builder;

import java.time.LocalTime;
import java.util.UUID;

@Builder
public record ScheduleCommand(
        Integer id,
        UUID empleadoId,
        LocalTime startTime,
        LocalTime endTime,
        LocalTime lunchStartTime,
        LocalTime lunchEndTime,
        Boolean vacation
) {
}
