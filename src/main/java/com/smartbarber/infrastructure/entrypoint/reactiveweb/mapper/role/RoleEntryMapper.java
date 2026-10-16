package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.role;

import com.smartbarber.application.command.in.role.RoleCommand;
import com.smartbarber.domain.model.role.Role;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.role.RoleRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.role.RoleRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleEntryMapper {

    RoleRsDto toResponse(Role role);

    default RoleCommand toCommand(RoleRqDto request) {
        return RoleCommand.builder()
                .roleType(request.roleType())
                .build();
    }
}
