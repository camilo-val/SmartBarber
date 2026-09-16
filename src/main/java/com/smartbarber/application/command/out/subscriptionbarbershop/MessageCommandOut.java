package com.smartbarber.application.command.out.subscriptionbarbershop;

import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record MessageCommandOut (
        UUID id,
        UUID barberId,
        Integer subscriptionId,
        UUID orderId,
        UUID transactionId,
        SubscriptionBarberStatus status,
        Integer amount,
        Integer duration,
        Instant createdAt,
        Instant updatedAt
){
}
