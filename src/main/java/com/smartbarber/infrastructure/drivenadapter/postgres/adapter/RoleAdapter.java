
package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.enums.RoleType;
import com.smartbarber.domain.model.role.Role;
import com.smartbarber.domain.port.RolePort;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.RoleData;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.RoleEntity;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.RoleAdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class RoleAdapter implements RolePort {

    private final RoleData roleData;
    private final RoleAdapterMapper mapper;

    @Override
    public Mono<Role> findById(Short id) {
        return roleData.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Role> save(Role role) {
        return roleData.save(mapper.toEntity(role))
                .doOnNext(entity ->
                        log.info("Role saved: {}", entity)
                )
                .doOnError(error ->
                        log.error("Error saving role", error)
                )
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Role> update(Short id, Role role) {
        return roleData.findById(id)
                .switchIfEmpty(Mono.error(
                        new IllegalArgumentException("Role not found: " + id)
                ))
                .map(existingEntity -> {
                    RoleEntity updatedEntity = mapper.toEntity(role);
                    updatedEntity.setId(existingEntity.getId());
                    return updatedEntity;
                })
                .flatMap(roleData::save)
                .doOnError(error ->
                        log.error("Error updating role with id {}", id, error)
                )
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByRoleType(RoleType roleType) {
        return roleData.findByRoleType(roleType.name())
                .hasElement();
    }
}
