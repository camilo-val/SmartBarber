package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartbarber.domain.enums.DocumentType;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record CustomerRsDto(
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
