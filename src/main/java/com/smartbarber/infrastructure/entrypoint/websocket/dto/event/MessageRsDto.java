package com.smartbarber.infrastructure.entrypoint.websocket.dto.event;

import lombok.Builder;

@Builder
public record MessageRsDto<T>(
        String type,
        T data
) {
}