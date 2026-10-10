package com.smartbarber.application.mapper;

import com.smartbarber.application.command.in.user.UserCommand;
import com.smartbarber.domain.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCommand toCommand(User user);
}
