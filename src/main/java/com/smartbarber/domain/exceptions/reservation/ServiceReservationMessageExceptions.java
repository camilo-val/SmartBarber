package com.smartbarber.domain.exceptions.reservation;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum ServiceReservationMessageExceptions implements ErrorMessage {
    INVALID_RESERVATION_DATA("SR_001", "Invalid data for reservation service"),
    SERVICE_RESERVATION_NOT_FOUND("SR_002", "The service reservation requested was not found"),
    INVALID_SERVICE_RESERVATION_STATUS("SR_003", "The service reservation status is not valid"),
    INVALID_DATE_RANGE("SR_005", "The reservation date is not valid"),
    SERVICE_RESERVATION_ALREADY_EXISTS("SR_006", "A reservation already exists for this time and employee"),
    CANNOT_CANCEL_SERVICE_RESERVATION("SR_009", "Cannot cancel a service reservation in this state"),
    RESERVATION_DATE_INVALID("SR_010", "The reservation date is not valid"),
    CANNOT_CONFIRM_SERVICE_RESERVATION("SR_011", "Cannot confirm a service reservation in this state");

    private final String code;
    private final String message;
    ServiceReservationMessageExceptions(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
