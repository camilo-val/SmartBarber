package com.smartbarber.application.usecase.role;

import com.smartbarber.application.command.in.role.RoleCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.Role.RoleMessageExceptions;
import com.smartbarber.domain.model.role.Role;
import com.smartbarber.domain.port.RolePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateRoleUC {

    private final RolePort rolePort;

    public Mono<Role> createRole(RoleCommand command) {
        return Mono.fromCallable(() ->
                        Role.createRole(null, command.roleType()))
                .flatMap(role ->
                        rolePort.existsByRoleType(role.getRoleType())
                                .flatMap(exists -> {
                                    if (Boolean.TRUE.equals(exists)) {
                                        return Mono.error(new BusinessExceptions(
                                                RoleMessageExceptions.ROLE_ALREADY_EXIST
                                        ));
                                    }
                                    return rolePort.save(role);
                                })
                );
    }
}
