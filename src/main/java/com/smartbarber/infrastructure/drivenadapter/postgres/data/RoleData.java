package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.RoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RoleData extends ReactiveCrudRepository<RoleEntity, Short> {

    Mono<RoleEntity> findByRoleType(String roleType);
}

