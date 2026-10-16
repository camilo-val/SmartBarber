package com.smartbarber.application.command.in.role;

import com.smartbarber.domain.enums.RoleType;
import lombok.Builder;

@Builder
public record RoleCommand(
        Short id,
        RoleType roleType,
        String status
) {
}

