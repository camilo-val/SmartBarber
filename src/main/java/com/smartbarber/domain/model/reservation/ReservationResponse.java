package com.smartbarber.domain.model.reservation;

import com.smartbarber.domain.enums.ReservationStatus;
import com.smartbarber.domain.enums.ReservationType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.reservation.ReservationMessageExceptions;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ReservationResponse {
    private final UUID id;
    private final UUID customerId;
    private final UUID employeeId;
    private final ReservationType reservationType;
    private final ReservationStatus status;
    private final Integer duration;
    private final Instant startTime;
    private final Instant endTime;
    private final String notes;
    private final Instant creationDate;
    private final Instant updatedDate;
    private final List<ServiceReservationResponse> serviceReservations;

    private ReservationResponse(Reservation reservation, List<ServiceReservationResponse> serviceReservations) {
        this.id = reservation.getId();
        this.customerId = reservation.getCustomerId();
        this.employeeId = reservation.getEmployeeId();
        this.reservationType = reservation.getReservationType();
        this.status = reservation.getStatus();
        this.duration = reservation.getDuration();
        this.startTime = reservation.getStartTime();
        this.endTime = reservation.getEndTime();
        this.notes = reservation.getNotes();
        this.creationDate = reservation.getCreationDate();
        this.updatedDate = reservation.getUpdatedDate();
        this.serviceReservations = serviceReservations;
    }

    public static ReservationResponse of(Reservation reservation, List<ServiceReservationResponse> serviceReservations) {
        if(reservation == null || serviceReservations == null || serviceReservations.isEmpty()) {
            throw new BusinessExceptions(ReservationMessageExceptions.RESERVATION_DATE_INVALID);
        }
        return new ReservationResponse(reservation, serviceReservations);
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public ReservationType getReservationType() {
        return reservationType;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public Integer getDuration() {
        return duration;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public String getNotes() {
        return notes;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public List<ServiceReservationResponse> getServiceReservations() {
        return serviceReservations;
    }
}
