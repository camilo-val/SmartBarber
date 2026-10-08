package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.model.schedule.Schedule;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ScheduleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel =  "spring")
public interface ScheduleAdapterMapper {
    ScheduleEntity toEntity(Schedule schedule);
    default Schedule toDomain(ScheduleEntity entity){
        if (entity == null){
            return null;
        }

        return Schedule.rebuild(
                entity.getId(),
                entity.getEmpleadoId(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getLunchStartTime(),
                entity.getLunchEndTime(),
                entity.getVacation()
        );
    }
}
