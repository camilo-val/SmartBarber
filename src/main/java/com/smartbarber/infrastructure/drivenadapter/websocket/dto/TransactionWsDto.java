package com.smartbarber.infrastructure.drivenadapter.websocket.dto;

import com.smartbarber.domain.enums.TransactionStatus;
import lombok.Builder;

import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;

@Builder
public record TransactionWsDto (
        UUID transactionId,
        UUID reservationId,
        UUID subscriptionBarberId,
        BigInteger amount,
        BigInteger discount,
        BigInteger subtotal,
        TransactionStatus status,
        UUID orderId,
        Instant createdAt,
        Instant updatedAt
){
}
