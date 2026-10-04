package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.enums.RoleType;
import com.smartbarber.domain.port.RolePort;
import com.smartbarber.domain.model.role.Role;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.RoleData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.RoleAdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class RoleAdapter implements RolePort {
    private final RoleData roleData;
    private final RoleAdapterMapper mapper;
    @Override
    public Mono<Role> findById(Short id){
        return roleData.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Role> save(Role role){
        return roleData.save(mapper.toEntity(role))
                .doOnNext(e -> log.info("Data registrada {}", e.toString()))
                .doOnSuccess(entityGuardada ->
                        log.info("Proceso de guardado finalizado correctamente"))
                .doOnError(error ->
                        log.error("error guardando role", role))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Role> update(Short id, Role role){
        return roleData.save(mapper.toEntity(role))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existByTypeRol(RoleType roleType){
        return roleData.findByRoleType(roleType)
                .map(mapper::toDomain).hasElement();
    }
}
