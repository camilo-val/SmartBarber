package com.smartbarber.application.command.in;

import com.smartbarber.domain.enums.SubscriptionType;
import lombok.Builder;

@Builder
public record SubscriptionTypeBarbershop(
    SubscriptionType type,
    SubscriptionBarbershopCommand subscriptionBarbershopCommand
){}
