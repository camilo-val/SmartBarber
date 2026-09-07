package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.model.user.User;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAdapterMapper {
    UserEntity toEntity(User user);
    default User toDomain(UserEntity entity){
        if (entity == null){
            return null;
        }
        return User.reconstruir(
                entity.getId(),
                entity.getFirebaseId(),
                entity.getEstado(),
                entity.getFechaCreacion(),
                entity.getFechaModificacion()
        );
    }
}
