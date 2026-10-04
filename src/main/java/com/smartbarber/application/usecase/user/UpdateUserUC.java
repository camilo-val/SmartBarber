package com.smartbarber.application.usecase.user;

import com.smartbarber.domain.model.user.User;
import com.smartbarber.domain.port.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdateUserUC {

    private final UserPort userPort;

    public Mono<User> userUpdate(String id, User user) {

        return userPort.findById(UUID.fromString(id))
                .map(existingUser -> User.update(
                        existingUser.getId(),
                        existingUser.getFirebaseId(),
                        existingUser.getStatus(),
                        existingUser.getCreateAt(),
                        Instant.now(),
                        user.getRoleId()
                ))
                .flatMap(updatedUser ->
                        userPort.update(UUID.fromString(id), updatedUser)
                );
    }
}