package com.smartbarber.infrastructure.entrypoint.websocket.dto;

import lombok.Builder;

@Builder
public record WsEvent<T>(
        String type,
        T data
) {
}