package com.smartbarber.domain.exceptions.notification;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum NotificationMessageExceptions implements ErrorMessage {

    NOTIFICATION_INVALID("N_001", "invalid notification"),
    NOTIFICATION_ALREADY_EXISTS("N_002", "notification already exists"),
    INVALID_DATA("N_003", "invalid data"),
    NOTIFICATION_NOT_FOUND("N_004", "notification not found");

    private final String code;
    private final String message;

    NotificationMessageExceptions(String code, String message) {
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
