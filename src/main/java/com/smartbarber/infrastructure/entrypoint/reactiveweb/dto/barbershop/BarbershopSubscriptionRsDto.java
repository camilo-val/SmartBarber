package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barbershop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.subscriptionbarber.SubscriptionBarbershopRsDto;
import lombok.Builder;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record BarbershopSubscriptionRsDto (
        UUID id,
        String name,
        String location,
        String phone,
        SubscriptionBarbershopRsDto subscription
){

}
