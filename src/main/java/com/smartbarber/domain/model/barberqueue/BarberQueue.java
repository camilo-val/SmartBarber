package com.smartbarber.domain.model.barberqueue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartbarber.domain.enums.NotificationType;
import com.smartbarber.domain.model.barbershop.BarbershopQueueResponse;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarberQueueEntity;
import lombok.Builder;

@Builder
public record BarberQueue(
        NotificationType type,
        @JsonIgnore
        BarberQueueEntity entity,
        BarbershopQueueResponse barbershop
      ) {
}
