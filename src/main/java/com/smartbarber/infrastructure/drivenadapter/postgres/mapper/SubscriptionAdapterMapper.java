package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.model.suscription.Subscription;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.SubscriptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionAdapterMapper {
    default Subscription toDomain(SubscriptionEntity entity){
        return Subscription.rebuild(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getCreateAt(),
                entity.getUpdateAt()
        );
    }
    SubscriptionEntity toEntity(Subscription subscription);
}
