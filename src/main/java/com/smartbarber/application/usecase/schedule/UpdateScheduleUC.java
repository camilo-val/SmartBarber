package com.smartbarber.application.usecase.schedule;

import com.smartbarber.domain.port.SchedulePort;
import com.smartbarber.domain.model.schedule.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class UpdateScheduleUC {
    private final SchedulePort schedulePort;

    public Mono<Schedule> scheduleUpdate(String id, Schedule schedule){
        return schedulePort.findById(Integer.valueOf(id))
                .map(scheduleDB -> Schedule.update(scheduleDB.getId(),schedule.getEmpleadoId(),schedule.getStartTime(),
                        schedule.getEndTime(),schedule.getLunchStartTime(),schedule.getLunchEndTime(),schedule.getVacation()))
                .flatMap(scheduleUpdate -> schedulePort.update(Integer.valueOf(id), scheduleUpdate));
    }
}
