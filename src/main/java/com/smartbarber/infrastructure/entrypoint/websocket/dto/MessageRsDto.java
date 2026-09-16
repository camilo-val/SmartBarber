package com.smartbarber.infrastructure.entrypoint.websocket.dto;

import com.smartbarber.application.command.in.SubscriptionBarbershopCommand;
import lombok.Builder;

@Builder
public record MessageRsDto<T>(
        String type,
        T data
) {
}