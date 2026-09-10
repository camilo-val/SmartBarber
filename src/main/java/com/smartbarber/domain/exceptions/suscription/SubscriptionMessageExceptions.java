package com.smartbarber.domain.exceptions.suscription;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum SubscriptionMessageExceptions implements ErrorMessage {

    SUBSCRIPTION_ALREADY_EXIST("ST_001","Subscription already exist"),
    SUBSCRIPTION_NOT_FOUND("ST_002","Subscription not found"),
    INVALID_SUBSCRIPTION("ST_003","invalid subscription");

    private final String code;
    private final String message;

    SubscriptionMessageExceptions(String code, String message) {
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
