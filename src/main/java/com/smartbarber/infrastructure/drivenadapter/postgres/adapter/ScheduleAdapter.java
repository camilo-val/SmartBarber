package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.port.SchedulePort;
import com.smartbarber.domain.model.schedule.Schedule;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.ScheduleData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.ScheduleAdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class ScheduleAdapter implements SchedulePort {

    private final ScheduleData scheduleData;
    private final ScheduleAdapterMapper mapper;

    @Override
    public Mono<Schedule> findById(Integer id) {
        return scheduleData.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Schedule> findByIdEmployee(UUID empleadoId) {
        return scheduleData.findByEmpleadoId(empleadoId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Schedule> save(Schedule schedule) {
        return scheduleData.save(mapper.toEntity(schedule))
                .doOnNext(e -> log.info("Data registrada {}", e))
                .doOnSuccess(entityGuardada ->
                        log.info("Proceso de guardado finalizado correctamente"))
                .doOnError(error ->
                        log.error("Error guardando horario", error))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Schedule> update(Integer id, Schedule schedule) {
        return scheduleData.save(mapper.toEntity(schedule))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existByIdEmployee(UUID empleadoId) {
        return scheduleData.existsByEmpleadoId(empleadoId);
    }
}