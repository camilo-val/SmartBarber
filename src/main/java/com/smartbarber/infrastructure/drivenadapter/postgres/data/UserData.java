package com.smartbarber.infrastructure.drivenadapter.postgres.data;

import com.smartbarber.infrastructure.drivenadapter.postgres.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserData extends ReactiveCrudRepository<UserEntity, UUID>{
    Mono<UserEntity> findByFirebaseId(String firebaseId);
}
