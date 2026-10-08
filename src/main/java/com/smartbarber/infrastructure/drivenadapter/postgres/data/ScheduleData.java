package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ScheduleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ScheduleData extends ReactiveCrudRepository<ScheduleEntity, Integer> {

    Mono<ScheduleEntity> findById(Integer id);

    Mono<ScheduleEntity> findByEmpleadoId(UUID empleadoId);

    Mono<Boolean> existsByEmpleadoId(UUID empleadoId);
}
