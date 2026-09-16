package com.smartbarber.domain.model.suscription;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.suscription.SubscriptionMessageExceptions;

import java.time.Instant;
import java.util.UUID;

public class Subscription {
    private final Integer id;
    private final String name;
    private final String description;
    private final Integer price;
    private final Instant createAt;
    private final Instant updateAt;


    private Subscription(Integer id, String name, String description, Integer price, Instant createAt, Instant updateAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static Subscription create(String name, String description, Integer price) {
        boolean isInvalid = isBlankOrNull(name) && isBlankOrNull(description);
        if(isInvalid || price == null || price <= 0){
            throw new BusinessExceptions(SubscriptionMessageExceptions.INVALID_SUBSCRIPTION);
        }
        return new Subscription(null, name, description, price, Instant.now(),null);
    }

    public static Subscription rebuild(Integer id,String name, String description, Integer price, Instant createAt, Instant updateAt) {
        boolean isInvalid = isBlankOrNull(name) && isBlankOrNull(description);
        if(isInvalid || price == null || price <= 0 || id == null || createAt == null){
            throw new BusinessExceptions(SubscriptionMessageExceptions.INVALID_SUBSCRIPTION);
        }
        return new Subscription(id, name, description, price, createAt, updateAt);
    }

    private static boolean isBlankOrNull(String attribute){
        return attribute == null || attribute.isBlank();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}
