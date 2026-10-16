package com.smartbarber.application.usecase.role;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.Role.RoleMessageExceptions;
import com.smartbarber.domain.model.role.Role;
import com.smartbarber.domain.port.RolePort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class SearchRoleUC {

    private final RolePort rolePort;

    public Mono<Role> findById(Short id) {
        return rolePort.findById(id)
                .doOnNext(role -> log.info("Role found: {}", role))
                .switchIfEmpty(Mono.error(
                        new BusinessExceptions(RoleMessageExceptions.ROLE_NOT_FOUND)
                ));
    }
}
