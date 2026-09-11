package com.smartbarber.domain.exceptions.subscriptionbarber;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum SubscriptionBarberMessageExceptions implements ErrorMessage {
    INVALID_SUBSCRIPTION_BARBER("SB_001","invalid subscription barber"),
    SUBSCRIPTION_BARBER_NOT_FOUND("SB_002","subscription barber not found"),
    SUBSCRIPTION_BARBER_ALREADY_EXISTS("SB_003","subscription barber already exists"),;
    private final String code;
    private final String message;
    SubscriptionBarberMessageExceptions(String code, String message) {
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
