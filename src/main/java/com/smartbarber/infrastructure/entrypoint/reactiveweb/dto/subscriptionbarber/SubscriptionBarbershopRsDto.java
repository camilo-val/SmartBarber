package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.subscriptionbarber;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import lombok.Builder;

import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record SubscriptionBarbershopRsDto (
        UUID id,
        UUID barberId,
        Integer subscriptionId,
        SubscriptionBarberStatus status,
        Integer duration,
        BigInteger subscriptionPrice,
        Byte subscriptionDiscount,
        UUID orderId,
        Instant createdAt,
        Instant updatedAt,
        Instant startDate,
        Instant expirationDate
){

}
