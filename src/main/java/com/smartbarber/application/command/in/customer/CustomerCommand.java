package com.smartbarber.application.command.in.customer;

import com.smartbarber.domain.enums.DocumentType;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record CustomerCommand(
        UUID id,
        UUID userId,
        String document,
        DocumentType documentType,
        String name,
        String cell,
        String email,
        Instant createAt,
        Instant updateAt
) {
}
