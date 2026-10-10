package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.reservationreview;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ReservationReviewRsDto (
        Integer id,
        UUID reservationId,
        Integer qualification,
        String comment,
        Instant createAt,
        Instant updateAt
){
}
