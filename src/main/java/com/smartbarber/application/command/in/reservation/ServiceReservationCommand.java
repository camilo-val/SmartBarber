package com.smartbarber.application.command.in.reservation;

import com.smartbarber.domain.enums.ServiceReservationStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ServiceReservationCommand (
    UUID id,
    UUID reservationId,
    Integer serviceId,
    ServiceReservationStatus status,
    Integer duration,
    Instant createAt,
    Instant updateAt){
}
