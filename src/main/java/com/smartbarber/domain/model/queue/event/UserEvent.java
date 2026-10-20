package com.smartbarber.domain.model.queue.event;

import lombok.Builder;

@Builder
public record UserEvent(
        String type,
        Object payload
) {}