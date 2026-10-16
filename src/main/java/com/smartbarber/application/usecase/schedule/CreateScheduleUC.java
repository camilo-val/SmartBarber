package com.smartbarber.application.usecase.schedule;

import com.smartbarber.application.command.in.schedule.ScheduleCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.schedule.ScheduleMessageExceptions;
import com.smartbarber.domain.model.schedule.Schedule;
import com.smartbarber.domain.port.SchedulePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateScheduleUC {

    private final SchedulePort schedulePort;

    public Mono<Schedule> createSchedule(ScheduleCommand command) {

        Schedule schedule = Schedule.createSchedule(
                null,
                command.empleadoId(),
                command.startTime(),
                command.endTime(),
                command.lunchStartTime(),
                command.lunchEndTime(),
                command.vacation()
        );

        return schedulePort.existByIdEmployee(schedule.getEmpleadoId())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new BusinessExceptions(
                                ScheduleMessageExceptions.SCHEDULE_ALREADY_EXISTS
                        ));
                    }

                    return Mono.just(schedule);
                })
                .flatMap(schedulePort::save);
    }
}
