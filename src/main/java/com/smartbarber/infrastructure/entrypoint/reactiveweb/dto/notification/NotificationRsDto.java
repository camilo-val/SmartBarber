package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.notification;

import java.time.Instant;
import java.util.UUID;

public record NotificationRsDto(
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
