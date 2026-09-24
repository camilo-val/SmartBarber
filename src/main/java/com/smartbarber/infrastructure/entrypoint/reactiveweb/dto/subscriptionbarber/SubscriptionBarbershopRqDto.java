package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.subscriptionbarber;

import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import com.smartbarber.domain.enums.SubscriptionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.math.BigInteger;
import java.util.UUID;

@Builder
public record SubscriptionBarbershopRqDto(
        @NotNull(message = "El barberId es obligatorio")
        UUID barberId,
        @NotNull(message = "El subscriptionId es obligatorio")
        Integer subscriptionId,
        @NotNull(message = "El status es obligatorio")
        SubscriptionBarberStatus status,
        @NotNull(message = "El duration es obligatorio")
        Integer duration,
        @NotNull(message = "El type es obligatorio")
        SubscriptionType type
){
}
