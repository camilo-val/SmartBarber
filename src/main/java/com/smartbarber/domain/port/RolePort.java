package com.smartbarber.domain.port;

import com.smartbarber.domain.enums.RoleType;
import com.smartbarber.domain.model.role.Role;
import reactor.core.publisher.Mono;

public interface RolePort {
    Mono<Role> findById(Short id);
    Mono<Role> save(Role role);
    Mono<Role> update(Short id, Role role);
    Mono<Boolean> existByTypeRol(RoleType roleType);
}
