package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.authorization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartbarber.domain.enums.RoleType;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthorizationRsDto (
        RoleType role,
        UUID userId,
        UUID employeeId,
        UUID barbershopId,
        UUID customerId

){
}
