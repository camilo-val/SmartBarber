package com.smartbarber.application.usecase.user;

import com.smartbarber.application.command.in.user.UserCommand;
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

    public Mono<User> userUpdate(String id, UserCommand user) {

        return userPort.findById(UUID.fromString(id))
                .map(existingUser -> existingUser.update(
                        existingUser.getFirebaseId(),
                        existingUser.getStatus(),
                        user.roleId()
                ))
                .flatMap(updatedUser ->
                        userPort.update(UUID.fromString(id), updatedUser)
                );
    }
}