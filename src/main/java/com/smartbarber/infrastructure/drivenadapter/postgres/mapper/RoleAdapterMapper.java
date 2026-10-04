package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.enums.RoleType;
import com.smartbarber.domain.model.role.Role;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleAdapterMapper {
    RoleEntity toEntity(Role role);
    default Role toDomain(RoleEntity entity){
        if (entity == null){
            return null;
        }

        return Role.rebuild(
                entity.getId(),
                mapTipoRol(entity.getRoleType()),
                entity.getStatus()
        );
    }
    default RoleType mapTipoRol(String tipoRol){
        return tipoRol == null
                ? null
                : RoleType.valueOf(tipoRol);
    }
}
