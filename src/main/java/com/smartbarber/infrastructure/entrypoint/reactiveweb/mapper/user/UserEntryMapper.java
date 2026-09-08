package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.user;

import com.smartbarber.domain.model.user.User;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User.UserRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User.UserRsDto;
import org.mapstruct.Mapper;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface UserEntryMapper {
    UserRsDto toResponse(User user);
    default User toDomain(UserRqDto rqDto){
        return User.crear(
                null,
                rqDto.firebaseId()
        );
    }
}
