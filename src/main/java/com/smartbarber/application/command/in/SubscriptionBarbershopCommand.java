package com.smartbarber.application.command.in;

import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import com.smartbarber.domain.enums.SubscriptionType;
import lombok.Builder;

import java.math.BigInteger;
import java.util.UUID;

@Builder
public record SubscriptionBarbershopCommand (
        UUID barberId,
        Integer subscriptionId,
        SubscriptionBarberStatus status,
        Integer duration,
        BigInteger subscriptionPrice,
        Byte subscriptionDiscount,
        SubscriptionType type
){
}
