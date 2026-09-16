package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.subscription;

import com.smartbarber.application.command.in.SubscriptionCommand;
import com.smartbarber.domain.model.suscription.Subscription;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.subscription.SubscriptionRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.subscription.SubscriptionRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionEntryMapper {
    SubscriptionCommand toCommand (SubscriptionRqDto request);
    SubscriptionRsDto toResponse (Subscription subscription);
}
