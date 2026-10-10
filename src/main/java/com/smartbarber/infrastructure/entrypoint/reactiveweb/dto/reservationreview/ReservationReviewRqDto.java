package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.reservationreview;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ReservationReviewRqDto (
        @NotNull(message = "El id de la reserva es obligatorio")
        UUID reservationId,

        Integer qualification,

        String comment
){
}
