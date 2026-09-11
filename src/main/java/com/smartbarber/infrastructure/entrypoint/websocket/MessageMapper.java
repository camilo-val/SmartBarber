package com.smartbarber.infrastructure.entrypoint.websocket;

import com.smartbarber.application.command.MessageCommand;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "currency", constant = "COP")
    @Mapping(target = "description", constant = "transaction in process")
    @Mapping(target = "orderId", source = "orderId")
    MessageCommand toRequest(SubscriptionBarbershop subscriptionBarbershop);
}
