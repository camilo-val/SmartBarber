package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.role;

import com.smartbarber.domain.enums.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record RoleRqDto (
        @NotNull
        RoleType roleType
){
}
