package com.smartbarber.domain.model.subscriptionbarber;

import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.subscriptionbarber.SubscriptionBarberMessageExceptions;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class SubscriptionBarbershop {
    private final UUID id;
    private final UUID barberId;
    private final Integer subscriptionId;
    private final UUID orderId;
    private final UUID transactionId;
    private final SubscriptionBarberStatus status;
    private final Integer amount;
    private final Integer duration;
    private final Instant createdAt;
    private final Instant updatedAt;


    private SubscriptionBarbershop(UUID id, UUID barberId, Integer subscriptionId, UUID orderId, UUID transactionId, SubscriptionBarberStatus status, Integer amount, Integer duration, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.barberId = barberId;
        this.subscriptionId = subscriptionId;
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.status = status;
        this.amount = amount;
        this.duration = duration;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static SubscriptionBarbershop create(UUID barberId, Integer subscriptionId, UUID orderId, UUID transactionId, Integer amount, Integer duration){

        boolean attributeIsNull = isNull(barberId) || isNull(subscriptionId) || isNull(orderId)
                || isNull(transactionId) || isNull(amount) || isNull(duration);

        if(attributeIsNull || amount <= 0 || duration <= 0){
            throw new BusinessExceptions(SubscriptionBarberMessageExceptions.INVALID_SUBSCRIPTION_BARBER);
        }

        return new SubscriptionBarbershop(null, barberId, subscriptionId,orderId, transactionId, SubscriptionBarberStatus.PENDING, amount,
                duration, Instant.now(),null);
    }

    public static SubscriptionBarbershop rebuild(UUID id, UUID barberId, Integer subscriptionId, UUID orderId, UUID transactionId, SubscriptionBarberStatus status, Integer amount, Integer duration, Instant createdAt, Instant updatedAt){

        boolean attributeIsNull =  isNull(id) || isNull(barberId) || isNull(subscriptionId) || isNull(orderId)
                || isNull(transactionId) || isNull(amount) || isNull(duration) || isNull(createdAt);


        if(attributeIsNull || amount <= 0 || duration <= 0){
            throw new BusinessExceptions(SubscriptionBarberMessageExceptions.INVALID_SUBSCRIPTION_BARBER);
        }

        return new SubscriptionBarbershop(null, barberId, subscriptionId,orderId, transactionId, status, amount,
                duration, createdAt,updatedAt);
    }

    private static boolean isNull(Object attribute){
        return Objects.isNull(attribute);
    }


    public UUID getId() {
        return id;
    }

    public UUID getBarberId() {
        return barberId;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public SubscriptionBarberStatus getStatus() {
        return status;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getDuration() {
        return duration;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
