package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.schedule;

import com.smartbarber.domain.model.schedule.Schedule;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.schedule.ScheduleRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.schedule.ScheduleRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleEntryMapper {
    ScheduleRsDto toResponse(Schedule schedule);
    default Schedule toDomain(ScheduleRqDto rqDto){
        return Schedule.createSchedule(
                null,
                rqDto.empleadoId(),
                rqDto.startTime(),
                rqDto.endTime(),
                rqDto.lunchStartTime(),
                rqDto.lunchEndTime(),
                rqDto.vacation()
        );
    }
}
