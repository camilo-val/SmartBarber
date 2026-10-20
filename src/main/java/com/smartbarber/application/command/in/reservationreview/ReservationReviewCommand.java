package com.smartbarber.application.command.in.reservationreview;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ReservationReviewCommand(
        Integer id,
        UUID reservationId,
        Integer qualification,
        String comment,
        Instant createAt,
        Instant updateAt
) {
}
