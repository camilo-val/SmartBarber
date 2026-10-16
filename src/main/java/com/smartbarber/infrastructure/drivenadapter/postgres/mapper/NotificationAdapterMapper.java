package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.model.notification.Notification;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.NotificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationAdapterMapper {

    NotificationEntity toEntity(Notification notification);

    default Notification toDomain(NotificationEntity entity) {
        if (entity == null) {
            return null;
        }

        return Notification.rebuild(
                entity.getId(),
                entity.getCustomerId(),
                entity.getReservationId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getChannelType(),
                entity.getEventType(),
                entity.getStatus(),
                entity.getRetries(),
                entity.getMessageError(),
                entity.getScheduledDate(),
                entity.getDateSent(),
                entity.getCreateAt()
        );
    }
}
