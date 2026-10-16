package com.smartbarber.application.command.in.service;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ServiceCommand(
        Integer id,
        UUID barberiaId,
        String name,
        String description,
        Integer duration,
        Boolean status,
        Boolean offer,
        Integer price,
        Integer specialPrice,
        Instant createAt,
        Instant updateAt
) {
}
