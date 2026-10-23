package com.smartbarber.domain.model.reservation;

import com.smartbarber.domain.enums.ReservationStatus;
import com.smartbarber.domain.enums.ReservationType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.reservation.ReservationMessageExceptions;
import com.smartbarber.domain.model.customer.CustomerResponse;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class ReservationResponse {
    private final UUID id;
    private final CustomerResponse customer;
    private final UUID employeeId;
    private final ReservationType reservationType;
    private final ReservationStatus status;
    private final Integer duration;
    private final OffsetDateTime startTime;
    private final OffsetDateTime endTime;
    private final String notes;
    private final OffsetDateTime creationDate;
    private final OffsetDateTime updatedDate;
    private final OffsetDateTime queueEntryTime;
    private final List<ServiceReservationResponse> serviceReservations;

    private ReservationResponse(Reservation reservation, List<ServiceReservationResponse> serviceReservations,CustomerResponse customer) {
        this.id = reservation.getId();
        this.customer = customer;
        this.employeeId = reservation.getEmployeeId();
        this.reservationType = reservation.getReservationType();
        this.status = reservation.getStatus();
        this.duration = reservation.getDuration();
        this.startTime = reservation.getStartTime();
        this.endTime = reservation.getEndTime();
        this.notes = reservation.getNotes();
        this.creationDate = reservation.getCreationDate();
        this.updatedDate = reservation.getUpdatedDate();
        this.queueEntryTime = reservation.getQueueEntryTime();
        this.serviceReservations = serviceReservations;
    }

    public static ReservationResponse of(Reservation reservation, List<ServiceReservationResponse> serviceReservations, CustomerResponse customer) {
        if(reservation == null || serviceReservations == null || serviceReservations.isEmpty()) {
            throw new BusinessExceptions(ReservationMessageExceptions.RESERVATION_DATE_INVALID);
        }
        return new ReservationResponse(reservation, serviceReservations,customer);
    }

    public UUID getId() {
        return id;
    }

    public CustomerResponse getCustomer() {
        return customer;
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

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public String getNotes() {
        return notes;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public OffsetDateTime getUpdatedDate() {
        return updatedDate;
    }

    public OffsetDateTime getQueueEntryTime() {
        return queueEntryTime;
    }

    public List<ServiceReservationResponse> getServiceReservations() {
        return serviceReservations;
    }
}
