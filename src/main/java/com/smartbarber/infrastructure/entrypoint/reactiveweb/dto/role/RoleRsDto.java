package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.role;

import com.smartbarber.domain.enums.RoleType;
import lombok.Builder;

@Builder
public record RoleRsDto (
        Short id,
        RoleType roleType,
        String status
){
}
