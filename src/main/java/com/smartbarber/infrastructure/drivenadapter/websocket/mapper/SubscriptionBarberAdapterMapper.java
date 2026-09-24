package com.smartbarber.infrastructure.drivenadapter.websocket.mapper;

import com.smartbarber.application.command.in.SubscriptionBarbershopCommand;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionBarberAdapterMapper {
    default SubscriptionBarbershop toDomain(SubscriptionBarbershopCommand command){
        return SubscriptionBarbershop
                .rebuild(null,
                command.barberId(),
                command.subscriptionId(),
                command.status(),
                command.duration(),
                command.subscriptionPrice(),
                command.subscriptionDiscount(),
                null,
                null,
                null,
                 null);
    }
}
