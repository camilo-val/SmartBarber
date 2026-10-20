package com.smartbarber.infrastructure.entrypoint.websocket.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueueEvent {

    private String type;
    private Object payload;

}