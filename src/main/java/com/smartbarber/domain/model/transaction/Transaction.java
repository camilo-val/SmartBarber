package com.smartbarber.domain.model.transaction;

import com.smartbarber.domain.enums.TransactionStatus;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.transaction.TransactionMessageExceptions;

import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;

public class Transaction {
    private final UUID transactionId;
    private final UUID reservationId;
    private final UUID subscriptionBarberId;
    private final BigInteger amount;
    private final BigInteger discount;
    private final BigInteger subtotal;
    private final TransactionStatus status;
    private final UUID orderId;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Transaction(UUID transactionId, UUID reservationId, UUID subscriptionBarberId, BigInteger amount,
                        BigInteger discount, BigInteger subtotal, TransactionStatus status, UUID orderId, Instant createdAt,
                        Instant updatedAt) {
        this.transactionId = transactionId;
        this.reservationId = reservationId;
        this.subscriptionBarberId = subscriptionBarberId;
        this.amount = amount;
        this.discount = discount;
        this.subtotal = subtotal;
        this.status = status;
        this.orderId = orderId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Transaction createReservationTransaction(UUID reservationId, BigInteger amount,
                                                           Byte discount) {
        BigInteger totalDiscount = amount.multiply(BigInteger.valueOf(discount)).divide(BigInteger.valueOf(100));
        UUID generateUUID = UUID.randomUUID();
        Instant now = Instant.now();
        isNullOrBlank(reservationId);
        isNullOrBlank(amount);
        isNullOrBlank(discount);
        validateAmount(amount);
        return new Transaction(null, reservationId, null, amount, totalDiscount, amount.subtract(totalDiscount),
                TransactionStatus.PENDING, generateUUID, now, null);
    }

    public static Transaction createSubscriptionTransaction(UUID subscriptionBarberId, BigInteger amount,
                                                            Byte discount) {
        BigInteger totalDiscount = amount.multiply(BigInteger.valueOf(discount)).divide(BigInteger.valueOf(100));
        UUID generateUUID = UUID.randomUUID();
        Instant now = Instant.now();
        isNullOrBlank(subscriptionBarberId);
        isNullOrBlank(amount);
        isNullOrBlank(discount);
        validateAmount(amount);
        return new Transaction(null, null, subscriptionBarberId, amount.subtract(totalDiscount), totalDiscount, amount,
                TransactionStatus.PENDING, generateUUID, now, null);
    }

    public Transaction processTransaction(TransactionStatus transactionStatus){

        if (transactionStatus != TransactionStatus.PENDING){
            return new Transaction(this.transactionId, this.reservationId, this.subscriptionBarberId, this.amount, this.discount, this.subtotal,
                    transactionStatus, this.orderId, this.createdAt, Instant.now());
        } else {
            throw new BusinessExceptions(TransactionMessageExceptions.INVALID_STATUS);
        }

    }

    public static Transaction rebuild(UUID transactionId, UUID reservationId, UUID subscriptionBarberId, BigInteger amount,
                                      BigInteger discount, BigInteger subtotal, TransactionStatus status, UUID orderId,
                                      Instant createdAt, Instant updatedAt){
        isNullOrBlank(transactionId);
        isNullOrBlank(amount);
        isNullOrBlank(discount);
        isNullOrBlank(subtotal);
        isNullOrBlank(status);
        isNullOrBlank(orderId);
        isNullOrBlank(createdAt);
        validateAmount(amount);
        return new Transaction(transactionId, reservationId, subscriptionBarberId, amount, discount, subtotal, status,
                orderId, createdAt, updatedAt);

    }

    private static void validateAmount(BigInteger amount){
        if (amount.compareTo(BigInteger.ZERO) <= 0){
            throw new BusinessExceptions(TransactionMessageExceptions.INVALID_TRANSACTION);
        }
    }
    public static void isNullOrBlank(Object attribute){

        if (attribute == null){
            throw new BusinessExceptions(TransactionMessageExceptions.INVALID_TRANSACTION);
        }

        if (attribute instanceof String string && string.isBlank()){
                throw new BusinessExceptions(TransactionMessageExceptions.INVALID_TRANSACTION);
            }

    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public UUID getSubscriptionBarberId() {
        return subscriptionBarberId;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public BigInteger getDiscount() {
        return discount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public BigInteger getSubtotal() {
        return subtotal;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", reservationId=" + reservationId +
                ", subscriptionBarberId=" + subscriptionBarberId +
                ", amount=" + amount +
                ", discount=" + discount +
                ", subtotal=" + subtotal +
                ", status=" + status +
                ", orderId=" + orderId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
