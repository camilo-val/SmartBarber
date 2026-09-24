package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.subscription;

import lombok.Builder;

import java.math.BigInteger;
import java.time.Instant;

@Builder
public record SubscriptionRsDto(
        Integer id,
        String name,
        String description,
        BigInteger price,
        Byte discount,
        Instant createAt,
        Instant updateAt
){
}
