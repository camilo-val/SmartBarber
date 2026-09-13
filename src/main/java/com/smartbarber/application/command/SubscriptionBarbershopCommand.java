package com.smartbarber.application.command;

import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SubscriptionBarbershopCommand (
        UUID barberId,
        Integer subscriptionId,
        UUID orderId,
        UUID transactionId,
        SubscriptionBarberStatus status,
        Integer amount,
        Integer duration
){
}
