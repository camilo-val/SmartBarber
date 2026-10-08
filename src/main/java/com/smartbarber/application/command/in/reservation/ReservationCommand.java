package com.smartbarber.application.command.in.reservation;

import com.smartbarber.domain.enums.ReservationStatus;
import com.smartbarber.domain.enums.ReservationType;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record ReservationCommand (
        UUID id,
        UUID customerId,
        UUID employeeId,
        List<Integer> reservationServiceId,
        ReservationType reservationType,
        ReservationStatus status,
        Instant startTime,
        Instant endTime,
        String notes,
        Instant creationDate,
        Instant updatedDate
){

}
