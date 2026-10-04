package com.smartbarber.application.usecase.role;

import com.smartbarber.domain.port.RolePort;
import com.smartbarber.domain.model.role.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
public class RoleUpdateUC {
    private final RolePort rolePort;

    public Mono<Role> roleUpdate(Short id, Role role){
        return rolePort.findById(id)
                .map(existRole -> Role.update(existRole.getId(),role.getRoleType(),existRole.getStatus()))
                .flatMap(roleUpdate -> rolePort.update(id, roleUpdate));
    }
}
