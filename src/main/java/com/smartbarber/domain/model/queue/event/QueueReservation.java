package com.smartbarber.domain.model.queue.event;

import lombok.Builder;

@Builder
public record QueueReservation(
        String reservationId,
        String customerId
) {
}