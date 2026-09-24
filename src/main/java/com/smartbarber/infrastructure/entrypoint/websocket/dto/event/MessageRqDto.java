package com.smartbarber.infrastructure.entrypoint.websocket.dto.event;

import com.smartbarber.domain.enums.SubscriptionType;
import com.smartbarber.domain.enums.TransactionType;
import lombok.Builder;

@Builder
public record MessageRqDto<T>(
        TransactionType type,
        T data
) {
}