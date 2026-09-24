/*package com.smartbarber.infrastructure.entrypoint.websocket.mapper;

import com.smartbarber.application.command.in.SubscriptionTypeBarbershop;
import com.smartbarber.application.command.in.MessageCommand;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.infrastructure.entrypoint.websocket.dto.event.MessageRqDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "currency", constant = "COP")
    @Mapping(target = "description", constant = "transaction in process")
    @Mapping(target = "orderId", source = "orderId")
    MessageCommand toRequest(SubscriptionBarbershop subscriptionBarbershop);

    @Mapping(target = "typeEvent", source = "type")
    @Mapping(target = "subscriptionBarbershopCommand", source = "data")
    SubscriptionTypeBarbershop toRequest (MessageRqDto<SubscriptionTypeBarbershop> messageRqDto);
}
*/