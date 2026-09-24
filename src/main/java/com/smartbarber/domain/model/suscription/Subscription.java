package com.smartbarber.domain.model.suscription;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.suscription.SubscriptionMessageExceptions;

import java.math.BigInteger;
import java.time.Instant;

public class Subscription {
    private final Integer id;
    private final String name;
    private final String description;
    private final BigInteger price;
    private final Byte discount;
    private final Instant createAt;
    private final Instant updateAt;


    private Subscription(Integer id, String name, String description, BigInteger price, Byte discount, Instant createAt, Instant updateAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static Subscription create(String name, String description, BigInteger price, Byte discount) {
        boolean isInvalid = isBlankOrNull(name) && isBlankOrNull(description);
        if(isInvalid || price == null || price.compareTo(BigInteger.ZERO) <= 0 || discount == null || discount < 0){
            throw new BusinessExceptions(SubscriptionMessageExceptions.INVALID_SUBSCRIPTION);
        }
        return new Subscription(null, name, description, price, discount, Instant.now(),null);
    }

    public static Subscription rebuild(Integer id,String name, String description, BigInteger price, Byte discount, Instant createAt, Instant updateAt) {
        boolean isInvalid = isBlankOrNull(name) && isBlankOrNull(description);
        if(isInvalid || price == null || price.compareTo(BigInteger.ZERO) <= 0 || id == null || createAt == null || discount == null || discount < 0){
            throw new BusinessExceptions(SubscriptionMessageExceptions.INVALID_SUBSCRIPTION);
        }
        return new Subscription(id, name, description, price, discount, createAt, updateAt);
    }

    private static boolean isBlankOrNull(String attribute){
        return attribute == null || attribute.isBlank();
    }

    public Byte getDiscount() {
        return discount;
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

    public BigInteger getPrice() {
        return price;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
