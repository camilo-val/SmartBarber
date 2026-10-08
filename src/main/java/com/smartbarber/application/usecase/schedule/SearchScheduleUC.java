package com.smartbarber.application.usecase.schedule;

import com.smartbarber.domain.port.SchedulePort;
import com.smartbarber.domain.exceptions.schedule.ScheduleMessageExceptions;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.model.schedule.Schedule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class SearchScheduleUC {
    private final SchedulePort schedulePort;

    public Mono<Schedule> buscarPorId(Integer id){
        return schedulePort.findById(id)
                .doOnNext(schedule -> log.info("Datos encontrados {}", schedule))
                .switchIfEmpty(Mono.error(new BusinessExceptions(ScheduleMessageExceptions.SCHEDULE_NOT_FOUND)));
    }

    public Mono<Schedule> buscarPorIdEmpleado(UUID empleadoId){
        return schedulePort.findByIdEmployee(empleadoId)
                .doOnNext(schedule -> log.info("Datos encontrados {}", schedule))
                .switchIfEmpty(Mono.error(new BusinessExceptions(ScheduleMessageExceptions.SCHEDULE_NOT_FOUND)));
    }
}
