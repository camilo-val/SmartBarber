package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.user;

import com.smartbarber.application.command.in.user.UserCommand;
import com.smartbarber.domain.model.user.User;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User.UserRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User.UserRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntryMapper {

    UserRsDto toResponse(User user);

    default UserCommand toDomain(UserRqDto rqDto, String firebaseId) {
        return UserCommand.builder()
                .id(null)
                .firebaseId(firebaseId)
                .roleId(rqDto.roleId())
                .build();
    }

    default UserCommand toDomainForUpdate(UserRqDto rqDto) {
        return UserCommand.builder()
                .id(null)
                .firebaseId(null)
                .roleId(rqDto.roleId())
                .build();
    }
}