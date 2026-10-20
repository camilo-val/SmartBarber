package com.smartbarber.application.usecase.notification;

import com.smartbarber.application.command.in.notification.NotificationCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.notification.NotificationMessageExceptions;
import com.smartbarber.domain.model.notification.Notification;
import com.smartbarber.domain.port.NotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateNotificationUC {

    private final NotificationPort notificationPort;

    public Mono<Notification> updateNotification(
            UUID id,
            NotificationCommand command
    ) {
        return notificationPort.findById(id)
                .switchIfEmpty(Mono.error(
                        new BusinessExceptions(
                                NotificationMessageExceptions
                                        .NOTIFICATION_NOT_FOUND
                        )
                ))
                .flatMap(existingNotification ->
                        Mono.fromCallable(() ->
                                existingNotification.updateNotification(
                                        id,
                                        command.customerId(),
                                        command.reservationId(),
                                        command.title(),
                                        command.content(),
                                        command.channelType(),
                                        command.eventType(),
                                        command.status(),
                                        command.retries(),
                                        command.messageError(),
                                        command.scheduledDate(),
                                        command.dateSent()
                                )
                        ).flatMap(notification ->
                                notificationPort.update(id, notification)
                        )
                );
    }
}

