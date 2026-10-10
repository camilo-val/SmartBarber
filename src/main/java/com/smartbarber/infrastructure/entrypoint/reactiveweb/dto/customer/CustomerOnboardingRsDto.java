package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.customer;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User.UserRsDto;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record CustomerOnboardingRsDto(
        UUID id,
        UserRsDto user,
        String document,
        DocumentType documentType,
        String name,
        String cell,
        String email,
        Instant createAt,
        Instant updateAt
){
}
