package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.enums.RoleType;
import com.smartbarber.domain.model.role.Role;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleAdapterMapper {

    @Mapping(target = "roleType", expression = "java(role.getRoleType().name())")
    RoleEntity toEntity(Role role);

    default Role toDomain(RoleEntity entity) {
        if (entity == null) {
            return null;
        }

        return Role.rebuild(
                entity.getId(),
                mapRoleType(entity.getRoleType()),
                entity.getStatus()
        );
    }

    default RoleType mapRoleType(String roleType) {
        if (roleType == null) {
            return null;
        }

        return RoleType.valueOf(roleType);
    }
}
