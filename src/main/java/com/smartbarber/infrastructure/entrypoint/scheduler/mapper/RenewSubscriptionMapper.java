package com.smartbarber.infrastructure.entrypoint.scheduler.mapper;

import com.smartbarber.application.command.in.SubscriptionBarbershopCommand;
import com.smartbarber.domain.enums.SubscriptionType;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RenewSubscriptionMapper {

    default SubscriptionBarbershopCommand toRequest (SubscriptionBarbershop subscriptionBarbershop, SubscriptionType type) {
        if (subscriptionBarbershop == null) {
            return null;
        }
        return new SubscriptionBarbershopCommand(
                subscriptionBarbershop.getBarberId(),
                subscriptionBarbershop.getSubscriptionId(),
                subscriptionBarbershop.getStatus(),
                subscriptionBarbershop.getDuration(),
                subscriptionBarbershop.getSubscriptionPrice(),
                subscriptionBarbershop.getSubscriptionDiscount(),
                subscriptionBarbershop.getAutomaticRenew(),
                type
        );
    }
}
