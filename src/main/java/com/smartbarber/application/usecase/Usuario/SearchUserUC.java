package com.smartbarber.application.usecase.Usuario;

import com.smartbarber.application.port.UserPort;
import com.smartbarber.domain.exceptions.UserExceptions;
import com.smartbarber.domain.exceptions.MessageExceptionUser;
import com.smartbarber.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class SearchUserUC {
    private final UserPort userPort;

    public Mono<User> buscarPorId(String id){
        return userPort.buscarUsuarioPorId(UUID.fromString(id))
                .doOnNext( user -> log.info("Datos encontrados {}", user))
                .switchIfEmpty(Mono.error(new UserExceptions(MessageExceptionUser.USUARIO_NO_EXISTE)));
    }
}
