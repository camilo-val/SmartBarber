package com.smartbarber.application.usecase.role;

import com.smartbarber.domain.port.RolePort;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.Role.RoleMessageExceptions;
import com.smartbarber.domain.model.role.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateRoleUC {
    private final RolePort rolePort;

    public Mono<Role> crearRol(Role role){
        return rolePort.existByTypeRol(role.getRoleType())
                .flatMap(exist -> {
                    if (Boolean.TRUE.equals(exist)){
                        return Mono.error(() -> new BusinessExceptions(RoleMessageExceptions.ROLE_ALREADY_EXIST));
                    }
                    return Mono.just(role);
                })
                .flatMap(rolePort::save);
    }
}
