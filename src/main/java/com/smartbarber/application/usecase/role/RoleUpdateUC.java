package com.smartbarber.application.usecase.role;

import com.smartbarber.application.command.in.role.RoleCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.Role.RoleMessageExceptions;
import com.smartbarber.domain.model.role.Role;
import com.smartbarber.domain.port.RolePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@AllArgsConstructor
public class RoleUpdateUC {

    private final RolePort rolePort;

    public Mono<Role> updateRole(Short id, RoleCommand command) {
        return rolePort.findById(id)
                .switchIfEmpty(Mono.error(
                        new BusinessExceptions(RoleMessageExceptions.ROLE_NOT_FOUND)
                ))
                .flatMap(existingRole ->
                        Mono.fromCallable(() ->
                                        Role.update(
                                                existingRole.getId(),
                                                command.roleType(),
                                                existingRole.getStatus()
                                        ))
                                .flatMap(updatedRole -> {
                                    if (Objects.equals(
                                            existingRole.getRoleType(),
                                            updatedRole.getRoleType()
                                    )) {
                                        return rolePort.update(id, updatedRole);
                                    }

                                    return rolePort.existsByRoleType(updatedRole.getRoleType())
                                            .flatMap(exists -> {
                                                if (Boolean.TRUE.equals(exists)) {
                                                    return Mono.error(new BusinessExceptions(
                                                            RoleMessageExceptions.ROLE_ALREADY_EXIST
                                                    ));
                                                }

                                                return rolePort.update(id, updatedRole);
                                            });
                                })
                );
    }
}
