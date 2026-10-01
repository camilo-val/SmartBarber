package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barbershop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartbarber.domain.enums.DocumentType;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
