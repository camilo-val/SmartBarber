package com.smartbarber.application.command.in.barbershop;

import com.smartbarber.domain.enums.DocumentType;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record BarbershopCommand(
     UUID id,
     String name,
     String description,
     String location,
     String phone,
     String document,
     DocumentType documentType,
     String companyName,
     String status,
     Instant createAt,
     Instant updateAt
) {
}
