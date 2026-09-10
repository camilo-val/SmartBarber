package com.smartbarber.domain.model.suscription;

import java.time.Instant;
import java.util.UUID;

public class SubscriptionBarbershop {
    private final UUID id;
    private final UUID baberShopId;
    private final UUID subscriptionId;
    private final UUID orderId;
    private final Integer amount;
    private final Instant createAt;
    private final Instant updateAt;

    private SubscriptionBarbershop(UUID id, UUID baberShopId, UUID subscriptionId, UUID orderId, Integer amount, Instant createAt, Instant updateAt) {
        this.id = id;
        this.baberShopId = baberShopId;
        this.subscriptionId = subscriptionId;
        this.orderId = orderId;
        this.amount = amount;
        this.createAt = createAt;
        this.updateAt = updateAt;

    }


    public static SubscriptionBarbershop subscribeBarberShop (UUID id, UUID baberShopId, UUID subscriptionId, UUID orderId, Integer amount) {
        boolean isNull = isNull(id) || isNull(baberShopId) || isNull(subscriptionId) || isNull(orderId) || isNull(amount);
        if (isNull || amount <= 0) {
            throw new IllegalArgumentException("Invalid subscription barbershop data");
        }
        return new SubscriptionBarbershop(id, baberShopId, subscriptionId, orderId, amount, Instant.now(),null);
    }


    private static boolean isNull(Object attribute){
        return attribute == null;
    }
    public UUID getId() {
        return id;
    }

    public UUID getBaberShopId() {
        return baberShopId;
    }

    public UUID getSubscriptionId() {
        return subscriptionId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Integer getAmount() {
        return amount;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}
