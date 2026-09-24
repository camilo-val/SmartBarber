package com.smartbarber.application.command.in;

import com.smartbarber.domain.enums.TransactionStatus;
import lombok.Builder;

import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;

@Builder
public record TransactionCommand (
    String id,
    UUID userId,
    BigInteger amount,
    String currency,
    TransactionStatus status,
    String description,
    UUID orderId,
    Instant createdAt,
    Instant updatedAt){
}


