package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.SubscriptionBarbershopEntyty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionBarbershopAdapterMapper {
    default SubscriptionBarbershop toDomain(SubscriptionBarbershopEntyty entyty){
        if(entyty.getId()  == null){
            return null;
        }
        return SubscriptionBarbershop.rebuild(
                entyty.getId(),
                entyty.getBarberId(),
                entyty.getSubscriptionId(),
                entyty.getOrderId(),
                entyty.getTransactionId(),
                entyty.getStatus(),
                entyty.getAmount(),
                entyty.getDuration(),
                entyty.getCreatedAt(),
                entyty.getUpdatedAt()
        );
    }
    SubscriptionBarbershopEntyty toEntity(SubscriptionBarbershop domain);
}
