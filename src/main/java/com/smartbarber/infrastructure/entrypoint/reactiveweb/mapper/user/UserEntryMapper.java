package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.user;

import com.smartbarber.domain.model.user.User;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User.UserRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User.UserRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntryMapper {

    UserRsDto toResponse(User user);

    default User toDomain(UserRqDto rqDto, String firebaseId) {
        return User.createUser(
                null,
                firebaseId,
                rqDto.roleId()
        );
    }

    default User toDomainForUpdate(UserRqDto rqDto) {
        return User.createUser(
                null,
                null,
                rqDto.roleId()
        );
    }
}