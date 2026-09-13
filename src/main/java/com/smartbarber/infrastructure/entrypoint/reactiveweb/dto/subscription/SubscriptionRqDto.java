package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.subscription;

import lombok.Builder;

@Builder
public record SubscriptionRqDto (
        String name,
        String description,
        Integer price
){
}
