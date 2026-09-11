package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barbershop;

import com.smartbarber.domain.enums.DocumentType;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record BarbershopRsDto(
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
){

}
