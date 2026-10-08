package com.smartbarber.domain.port;

import com.smartbarber.domain.model.schedule.Schedule;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SchedulePort {

    Mono<Schedule> findById(Integer id);

    Mono<Schedule> findByIdEmployee(UUID empleadoId);

    Mono<Schedule> save(Schedule schedule);

    Mono<Schedule> update(Integer id, Schedule schedule);

    Mono<Boolean> existByIdEmployee(UUID empleadoId);
}
