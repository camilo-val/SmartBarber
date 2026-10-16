package com.smartbarber.application.usecase.notification;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.notification.NotificationMessageExceptions;
import com.smartbarber.domain.model.notification.Notification;
import com.smartbarber.domain.port.NotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SearchNotificationUC {

    private final NotificationPort notificationPort;

    public Mono<Notification> findById(UUID id) {
        return notificationPort.findById(id)
                .switchIfEmpty(Mono.error(
                        new BusinessExceptions(
                                NotificationMessageExceptions
                                        .NOTIFICATION_NOT_FOUND
                        )
                ));
    }

    public Flux<Notification> findByReservationId(UUID reservationId) {
        return notificationPort.findByIdReservation(reservationId);
    }

    public Flux<Notification> findByCustomerId(UUID customerId) {
        return notificationPort.findByIdCustomer(customerId);
    }
}

