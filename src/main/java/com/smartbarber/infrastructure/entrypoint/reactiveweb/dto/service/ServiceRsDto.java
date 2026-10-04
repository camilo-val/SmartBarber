package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.service;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ServiceRsDto(
        Integer id,
        UUID barberiaId,
        String name,
        String description,
        Integer duration,
        Boolean status,
        Boolean offert,
        Integer price,
        Integer special_price,
        Instant createAt,
        Instant updateAt
) {}
