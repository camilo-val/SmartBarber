package com.smartbarber.infrastructure.drivenadapter.websocket;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record MessageDto
        (        String id,
                 UUID userId,
                 BigDecimal amount,
                 String currency,
                 String status,
                 String description,
                 UUID orderId,
                 Instant createdAt,
                 Instant updatedAt) {
}
