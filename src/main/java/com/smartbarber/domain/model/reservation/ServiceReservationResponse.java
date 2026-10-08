package com.smartbarber.domain.model.reservation;

import com.smartbarber.domain.enums.ServiceReservationStatus;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.reservation.ServiceReservationMessageExceptions;

import java.time.Instant;
import java.util.UUID;

public class ServiceReservationResponse {
    private final UUID id;
    private final UUID reservationId;
    private final String serviceName;
    private final ServiceReservationStatus status;
    private final Integer duration;
    private final Instant createdAt;

    private ServiceReservationResponse(UUID id, UUID reservationId, String serviceName, ServiceReservationStatus status,
                                       Integer duration, Instant createdAt) {
        this.id = id;
        this.reservationId = reservationId;
        this.serviceName = serviceName;
        this.status = status;
        this.createdAt = createdAt;
        this.duration = duration;
    }

    public static ServiceReservationResponse rebuild (UUID id, UUID reservationId, String serviceName, ServiceReservationStatus status,
                                                      Integer duration, Instant createdAt){
        System.out.println("reservationId: " + reservationId + " serviceName: " + serviceName + " duration: " + duration);

        dataValidate(reservationId, serviceName);
        return new ServiceReservationResponse(id, reservationId, serviceName, ServiceReservationStatus.ACTIVE, duration, Instant.now());
    }
    private static void dataValidate(
            UUID reservationId,
            String serviceName) {
        if (reservationId == null || serviceName == null) {
            throw new BusinessExceptions(ServiceReservationMessageExceptions.INVALID_RESERVATION_DATA);
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ServiceReservationStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Integer getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "ServiceReservation{" +
                "id=" + id +
                ", reservationId=" + reservationId +
                ", serviceName=" + serviceName +
                ", status=" + status +
                ", duration=" + duration +
                ", createdAt=" + createdAt +
                '}';
    }
}

