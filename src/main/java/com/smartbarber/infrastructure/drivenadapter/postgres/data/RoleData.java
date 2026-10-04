package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.domain.enums.RoleType;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.RoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RoleData extends ReactiveCrudRepository<RoleEntity, Short>{
    Mono<RoleEntity> findByRoleType(RoleType roleType);
}
