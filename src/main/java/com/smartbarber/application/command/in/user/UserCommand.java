package com.smartbarber.application.command.in.user;

import com.smartbarber.domain.enums.UserStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record UserCommand (
         UUID id,
         String firebaseId,
         UserStatus status,
         Instant createAt,
         Instant updateAt,
         Short roleId
){
}
