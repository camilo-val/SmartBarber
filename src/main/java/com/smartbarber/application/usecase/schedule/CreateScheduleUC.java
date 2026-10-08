package com.smartbarber.application.usecase.schedule;

import com.smartbarber.domain.port.SchedulePort;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.schedule.ScheduleMessageExceptions;
import com.smartbarber.domain.model.schedule.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateScheduleUC {

    private final SchedulePort schedulePort;

    public Mono<Schedule> crearHorario(Schedule schedule){
        return schedulePort.existByIdEmployee(schedule.getEmpleadoId())
                .flatMap(exist -> {

                    if (Boolean.TRUE.equals(exist)){
                        return Mono.error(() -> new BusinessExceptions(
                                ScheduleMessageExceptions.SCHEDULE_ALREADY_EXISTS
                        ));
                    }

                    return Mono.just(schedule);
                })
                .flatMap(schedulePort::save);
    }
}
