package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.schedule;

import com.smartbarber.application.command.in.schedule.ScheduleCommand;
import com.smartbarber.domain.model.schedule.Schedule;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.schedule.ScheduleRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.schedule.ScheduleRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleEntryMapper {

    ScheduleRsDto toResponse(Schedule schedule);

    default ScheduleCommand toCommand(ScheduleRqDto request) {
        return ScheduleCommand.builder()
                .id(null)
                .empleadoId(request.empleadoId())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .lunchStartTime(request.lunchStartTime())
                .lunchEndTime(request.lunchEndTime())
                .vacation(request.vacation())
                .build();
    }
}