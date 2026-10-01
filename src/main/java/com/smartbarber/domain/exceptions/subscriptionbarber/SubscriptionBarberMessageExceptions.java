package com.smartbarber.domain.exceptions.subscriptionbarber;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum SubscriptionBarberMessageExceptions implements ErrorMessage {
    INVALID_SUBSCRIPTION_BARBER("SB_001","invalid subscription barber"),
    SUBSCRIPTION_BARBER_NOT_FOUND("SB_002","subscription barber not found"),
    SUBSCRIPTION_BARBER_ALREADY_EXISTS("SB_003","subscription barber already exists"),
    INVALID_SUBSCRIPTION_BARBER_STATUS("SB_004","invalid subscription barber status"),
    THE_BARBER_HAS_ALREADY_ACTIVE_OR_PENDING_SUBSCRIPTIONS("SB_005","the barber has already active or pending subscriptions"),
    THE_BARBER_HAS_ALREADY_APPROVED_OR_PENDING_SUBSCRIPTIONS("SB_005","the barber has already active or pending subscriptions"),
    INVALID_TYPE_SUBSCRIPTION("SB_006","invalid type subscription"),
    THE_BARBER_ALREADY_HAS_AN_ACTIVE_SUBSCRIPTION("SB_007","the barber already has an active subscription");
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
