package com.smartbarber.domain.model.subscriptionbarber;

import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.subscriptionbarber.SubscriptionBarberMessageExceptions;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class SubscriptionBarbershopResponse {
    private final UUID id;
    private final UUID barberId;
    private final Integer subscriptionId;
    private final SubscriptionBarberStatus status;
    private final Integer duration;
    private final BigInteger subscriptionPrice;
    private final Byte subscriptionDiscount;
    private final Boolean automaticRenew;
    private final Instant createdAt;
    private final UUID orderId;

    private SubscriptionBarbershopResponse(UUID id, UUID barberId, Integer subscriptionId,
                                           SubscriptionBarberStatus status, Integer duration, BigInteger subscriptionPrice, Byte subscriptionDiscount, Boolean automaticRenew, Boolean automaticRenew1, Instant createdAt, UUID orderId) {
        this.id = id;
        this.barberId = barberId;
        this.subscriptionId = subscriptionId;
        this.status = status;
        this.duration = duration;
        this.automaticRenew = automaticRenew;
        this.createdAt = createdAt;
        this.subscriptionPrice = subscriptionPrice;
        this.subscriptionDiscount = subscriptionDiscount;
        this.orderId = orderId;
    }

    public static SubscriptionBarbershopResponse rebuild(UUID id, UUID barberId, Integer subscriptionId,
                                                         SubscriptionBarberStatus status, Integer duration,
                                                         BigInteger price, Byte discount, Boolean automaticRenew,
                                                         Instant createdAt, UUID orderId){

        boolean attributeIsNull =  isNull(id) || isNull(barberId) || isNull(subscriptionId) || isNull(duration) || isNull(createdAt) || isNull(orderId);


        if(attributeIsNull || duration <= 0){
            throw new BusinessExceptions(SubscriptionBarberMessageExceptions.INVALID_SUBSCRIPTION_BARBER);
        }

        return new SubscriptionBarbershopResponse(id, barberId, subscriptionId, status, duration, price, discount, automaticRenew, automaticRenew, createdAt, orderId);
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

    public SubscriptionBarberStatus getStatus() {
        return status;
    }

    public Integer getDuration() {
        return duration;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public BigInteger getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public Byte getSubscriptionDiscount() {
        return subscriptionDiscount;
    }

    public Boolean getAutomaticRenew() {
        return automaticRenew;
    }

    public UUID getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "SubscriptionBarbershop{" +
                "id=" + id +
                ", barberId=" + barberId +
                ", subscriptionId=" + subscriptionId +
                ", status=" + status +
                ", duration=" + duration +
                ", price=" + subscriptionPrice +
                ", discount=" + subscriptionDiscount +
                ", createdAt=" + createdAt +
                ", orderId=" + orderId +
                '}';
    }
}
