package com.smartbarber.application.usecase.user;

import com.smartbarber.application.port.UserPort;
import com.smartbarber.domain.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdateUserUC {
    private final UserPort userPort;

    public Mono<User> userUpdate(String id, User user){

        return userPort.findById(UUID.fromString(id))
                .map( userPort -> User.update(userPort.getId(), user.getFirebaseId() ,user.getStatus(), userPort.getCreateAt(), Instant.now()))
                .flatMap(userUpdate -> userPort.update(UUID.fromString(id), userUpdate));
    }
}
