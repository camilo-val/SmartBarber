package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.subscription;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SubscriptionRqDto (
        UUID id,
        String name,
        String description,
        Integer price
){
}
