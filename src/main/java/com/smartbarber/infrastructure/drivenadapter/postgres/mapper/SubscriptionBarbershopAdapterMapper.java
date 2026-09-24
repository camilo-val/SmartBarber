package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.SubscriptionBarbershopEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionBarbershopAdapterMapper {
    default SubscriptionBarbershop toDomain(SubscriptionBarbershopEntity entyty){
        if(entyty.getId()  == null){
            return null;
        }
        return SubscriptionBarbershop.rebuild(
                entyty.getId(),
                entyty.getBarberId(),
                entyty.getSubscriptionId(),
                entyty.getStatus(),
                entyty.getDuration(),
                entyty.getSubscriptionPrice(),
                entyty.getSubscriptionDiscount(),
                entyty.getCreatedAt(),
                entyty.getUpdatedAt(),
                entyty.getStartDate(),
                entyty.getExpirationDate()

        );
    }
    SubscriptionBarbershopEntity toEntity(SubscriptionBarbershop domain);
}
