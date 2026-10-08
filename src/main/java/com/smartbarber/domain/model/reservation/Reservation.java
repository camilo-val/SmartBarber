package com.smartbarber.domain.model.reservation;

import com.smartbarber.domain.enums.ReservationStatus;
import com.smartbarber.domain.enums.ReservationType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.reservation.ReservationMessageExceptions;

import java.time.Instant;
import java.util.UUID;

public class Reservation {
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

    private Reservation(
            UUID id,
            UUID customerId,
            UUID employeeId,
            ReservationType reservationType,
            ReservationStatus status,
            Integer duration,
            Instant startTime,
            Instant endTime,
            String notes,
            Instant creationDate,
            Instant updatedDate) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.reservationType = reservationType;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
    }

    public static Reservation createReservation(
            UUID customerId,
            UUID employeeId,
            Integer duration,
            Instant startTime,
            Instant endTime,
            String notes) {
        dataValidate(customerId, employeeId, ReservationType.SERVICE_RESERVATION, startTime, endTime);
        hoursValidate(startTime, endTime);

        return new Reservation(
                null,
                customerId,
                employeeId,
                ReservationType.SERVICE_RESERVATION,
                ReservationStatus.PENDING,
                duration,
                startTime,
                endTime,
                notes,
                Instant.now(),
                null
        );
    }

    public static Reservation rebuild(
            UUID id,
            UUID customerId,
            UUID employeeId,
            ReservationType reservationType,
            ReservationStatus estado,
            Integer duration,
            Instant startTime,
            Instant endTime,
            String notes,
            Instant creationDate,
            Instant updatedDate) {
        dataValidate(customerId, employeeId, reservationType, startTime, endTime);
        hoursValidate(startTime, endTime);

        if (id == null || estado == null) {
            throw new BusinessExceptions(ReservationMessageExceptions.INVALID_RESERVATION_DATA);
        }

        return new Reservation(
                id,
                customerId,
                employeeId,
                reservationType,
                estado,
                duration,
                startTime,
                endTime,
                notes,
                creationDate,
                updatedDate
        );
    }

    public Reservation actualizar(
            UUID customerId,
            UUID employeeId,
            ReservationType reservationType,
            Integer duration,
            Instant startTime,
            Instant endTime,
            String notes) {
        dataValidate(customerId, employeeId, reservationType, startTime, endTime);
        hoursValidate(startTime, endTime);

        return new Reservation(
                this.id,
                customerId,
                employeeId,
                reservationType,
                this.status,
                duration,
                startTime,
                endTime,
                notes,
                this.creationDate,
                Instant.now()
        );
    }

    public Reservation confirmar() {
        if (status != ReservationStatus.PENDING) {
            throw new BusinessExceptions(ReservationMessageExceptions.CANNOT_CONFIRM_RESERVATION);
        }
        return new Reservation(
                this.id,
                this.customerId,
                this.employeeId,
                this.reservationType,
                ReservationStatus.CONFIRMED,
                this.duration,
                this.startTime,
                this.endTime,
                this.notes,
                this.creationDate,
                Instant.now()
        );
    }

    public Reservation cancelar() {
        if (status == ReservationStatus.COMPLETED || status == ReservationStatus.CANCELLED || status == ReservationStatus.NO_SHOW) {
            throw new BusinessExceptions(ReservationMessageExceptions.CANNOT_CANCEL_RESERVATION);
        }
        return new Reservation(
                this.id,
                this.customerId,
                this.employeeId,
                this.reservationType,
                ReservationStatus.CANCELLED,
                this.duration,
                this.startTime,
                this.endTime,
                this.notes,
                this.creationDate,
                Instant.now()
        );
    }

    public Reservation marcarEnProgreso() {
        if (status != ReservationStatus.CONFIRMED) {
            throw new BusinessExceptions(ReservationMessageExceptions.INVALID_RESERVATION_STATUS);
        }
        return new Reservation(
                this.id,
                this.customerId,
                this.employeeId,
                this.reservationType,
                ReservationStatus.IN_PROGRESS,
                this.duration,
                this.startTime,
                this.endTime,
                this.notes,
                this.creationDate,
                Instant.now()
        );
    }

    public Reservation completar() {
        if (status != ReservationStatus.IN_PROGRESS) {
            throw new BusinessExceptions(ReservationMessageExceptions.INVALID_RESERVATION_STATUS);
        }
        return new Reservation(
                this.id,
                this.customerId,
                this.employeeId,
                this.reservationType,
                ReservationStatus.COMPLETED,
                this.duration,
                this.startTime,
                this.endTime,
                this.notes,
                this.creationDate,
                Instant.now()
        );
    }

    public Reservation marcarNoShow() {
        if (status == ReservationStatus.COMPLETED || status == ReservationStatus.CANCELLED) {
            throw new BusinessExceptions(ReservationMessageExceptions.INVALID_RESERVATION_STATUS);
        }
        return new Reservation(
                this.id,
                this.customerId,
                this.employeeId,
                this.reservationType,
                ReservationStatus.NO_SHOW,
                this.duration,
                this.startTime,
                this.endTime,
                this.notes,
                this.creationDate,
                Instant.now()
        );
    }

    private static void dataValidate(
            UUID customerId,
            UUID employeeId,
            ReservationType reservationType,
            Instant startTime,
            Instant endTime) {
        if (customerId == null || employeeId == null ||
                reservationType == null || startTime == null || endTime == null) {
            throw new BusinessExceptions(ReservationMessageExceptions.INVALID_RESERVATION_DATA);
        }
    }

    private static void hoursValidate(Instant startTime, Instant endTime) {
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
                throw new BusinessExceptions(ReservationMessageExceptions.RESERVATION_DATE_INVALID);
        }
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

    public Integer getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", employeeId=" + employeeId +
                ", reservationType=" + reservationType +
                ", status=" + status +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", notes='" + notes + '\'' +
                ", creationDate=" + creationDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
