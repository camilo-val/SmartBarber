package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User;

import com.smartbarber.domain.enums.UserStatus;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record UserRsDto (
        UUID id,
        String firebaseId,
        UserStatus status,
        Instant createAt,
        Instant updateAt,
        Short roleId
){
}
