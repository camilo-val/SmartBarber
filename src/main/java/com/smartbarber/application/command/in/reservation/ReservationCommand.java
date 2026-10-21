package com.smartbarber.application.command.in.reservation;

import com.smartbarber.domain.enums.ReservationStatus;
import com.smartbarber.domain.enums.ReservationType;
import lombok.Builder;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record ReservationCommand (
        UUID id,
        UUID barbershopId,
        UUID customerId,
        UUID employeeId,
        List<Integer> reservationServiceId,
        ReservationType reservationType,
        ReservationStatus status,
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        String notes,
        OffsetDateTime creationDate,
        OffsetDateTime updatedDate,
        OffsetDateTime queueEntryTime
){

}
