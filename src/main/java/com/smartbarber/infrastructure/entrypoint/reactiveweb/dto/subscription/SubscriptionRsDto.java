package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.subscription;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record SubscriptionRsDto(
        Integer id,
        String name,
        String description,
        Integer price,
        Instant createAt,
        Instant updateAt
){
}
