package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.error;

import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;

@Builder
public record ErrorRsDto(
        String reason,
        String code,
        String message,
        Instant date
){
}
