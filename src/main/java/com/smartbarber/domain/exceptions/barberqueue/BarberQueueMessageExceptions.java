package com.smartbarber.domain.exceptions.barberqueue;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum BarberQueueMessageExceptions implements ErrorMessage {
    THE_QUEUE_BABER_DOES_NOT_CONFIGURATION("BQ_001","The queue barber does not configuration"),
    THE_BARBER_HAS_CONFIGURATION_ALREADY("BQ_002","The queue barber has configuration already");
    private final String code;
    private final String message;

    BarberQueueMessageExceptions(String code, String message) {
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
