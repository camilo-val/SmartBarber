package com.smartbarber.infrastructure.entrypoint.websocket.dto;

import com.smartbarber.application.command.in.SubscriptionBarbershopCommand;
import lombok.Builder;

@Builder
public record MessageRqDto(
        String type,
        SubscriptionBarbershopCommand data
) {
}