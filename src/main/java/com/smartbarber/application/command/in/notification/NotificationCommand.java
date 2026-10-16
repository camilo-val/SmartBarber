package com.smartbarber.application.command.in.notification;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record NotificationCommand(
        UUID id,
        UUID customerId,
        UUID reservationId,
        String title,
        String content,
        String channelType,
        String eventType,
        String status,
        Integer retries,
        String messageError,
        Instant scheduledDate,
        Instant dateSent,
        Instant createAt
) {
}
