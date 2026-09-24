package com.smartbarber.domain.enums;


public enum SubscriptionBarberStatus {
    APPROVED,
    PENDING,
    DECLINED,
    EXPIRED;

    public static SubscriptionBarberStatus mapStatus(
            TransactionStatus status) {

        return switch (status) {

            case PENDING ->
                    SubscriptionBarberStatus.PENDING;

            case APPROVED ->
                    SubscriptionBarberStatus.APPROVED;

            case REJECTED, REFUNDED, CANCELED ->
                    SubscriptionBarberStatus.DECLINED;

        };
    }
}
