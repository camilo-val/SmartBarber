package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.model.notification.Notification;
import com.smartbarber.domain.port.NotificationPort;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.NotificationData;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.NotificationEntity;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.NotificationAdapterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NotificationAdapter implements NotificationPort {

    private final NotificationData notificationData;
    private final NotificationAdapterMapper notificationAdapterMapper;

    @Override
    public Mono<Notification> findById(UUID id) {
        return notificationData.findById(id)
                .map(notificationAdapterMapper::toDomain);
    }

    @Override
    public Mono<Notification> save(Notification notification) {
        NotificationEntity entity =
                notificationAdapterMapper.toEntity(notification);

        return notificationData.save(entity)
                .map(notificationAdapterMapper::toDomain);
    }

    @Override
    public Mono<Notification> update(UUID id, Notification notification) {
        return notificationData.findById(id)
                .flatMap(existingEntity -> {
                    NotificationEntity updatedEntity =
                            notificationAdapterMapper.toEntity(notification);

                    updatedEntity.setId(existingEntity.getId());

                    return notificationData.save(updatedEntity);
                })
                .map(notificationAdapterMapper::toDomain);
    }

    @Override
    public Flux<Notification> findByIdReservation(UUID reservationId) {
        return notificationData.findAllByReservationId(reservationId)
                .map(notificationAdapterMapper::toDomain);
    }

    @Override
    public Flux<Notification> findByIdCustomer(UUID customerId) {
        return notificationData.findAllByCustomerId(customerId)
                .map(notificationAdapterMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existByTitleAndContent(
            String title,
            String content
    ) {
        return notificationData.existsByTitleAndContent(title, content);
    }
}
