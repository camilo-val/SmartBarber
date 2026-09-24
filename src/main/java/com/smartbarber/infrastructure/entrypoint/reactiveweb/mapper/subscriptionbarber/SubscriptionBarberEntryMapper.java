package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.subscriptionbarber;

import com.smartbarber.application.command.in.SubscriptionBarbershopCommand;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershopResponse;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.subscriptionbarber.SubscriptionBarbershopRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.subscriptionbarber.SubscriptionBarbershopRsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionBarberEntryMapper {
    SubscriptionBarbershopCommand toCommand(SubscriptionBarbershopRqDto request);
    SubscriptionBarbershopRsDto toResponse (SubscriptionBarbershop domain);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "barberId", source = "barberId")
    @Mapping(target = "subscriptionId", source = "subscriptionId")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "subscriptionPrice", source = "subscriptionDiscount")
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "createdAt", source = "createdAt")
    SubscriptionBarbershopRsDto toCreateResponse(SubscriptionBarbershopResponse domain);
}
