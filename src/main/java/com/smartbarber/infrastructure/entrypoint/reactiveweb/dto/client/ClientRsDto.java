package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.client;

import com.smartbarber.domain.enums.DocumentType;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ClientRsDto (
        UUID id,
        UUID userId,
        String document,
        DocumentType documentType,
        String name,
        String cell,
        String email,
        Instant createAt,
        Instant updateAt
){
}
