
package com.smartbarber.domain.model.queue;

import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.customer.CustomerRsDto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record QueueSnapshot(
        UUID employeeId,
        boolean acceptingClients,
        QueueTurn currentTurn,
        List<QueueTurn> waitingTurns,
        OffsetDateTime updatedAt
) {
    public record QueueTurn(
            UUID reservationId,
            CustomerRsDto customer,
            int position,
            int estimatedWaitMinutes,
            Integer durationMinutes,
            String status
    ) {
    }
}
