package com.smartbarber.application.usecase.notification;

import com.smartbarber.application.command.in.notification.NotificationCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.notification.NotificationMessageExceptions;
import com.smartbarber.domain.model.notification.Notification;
import com.smartbarber.domain.port.NotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreateNotificationUC {

    private final NotificationPort notificationPort;

    public Mono<Notification> createNotification(
            NotificationCommand command
    ) {
        return Mono.fromCallable(() ->
                Notification.createNotification(
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
                notificationPort.existByTitleAndContent(
                        notification.getTitle(),
                        notification.getContent()
                ).flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(
                                new BusinessExceptions(
                                        NotificationMessageExceptions
                                                .NOTIFICATION_ALREADY_EXISTS
                                )
                        );
                    }

                    return notificationPort.save(notification);
                })
        );
    }
}

