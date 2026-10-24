package com.smartbarber.domain.model.queue.notification;

import com.smartbarber.domain.enums.NotificationType;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Notification(
        NotificationType type,
        Barbershop barbershop,
        OffsetDateTime timestamp
) {

    public record Barbershop(
            UUID id,
            String name,
            String address,
            Barber barber
    ) {
    }

    public record Barber(
            UUID id,
            String name,
            String phone
    ) {
    }
}