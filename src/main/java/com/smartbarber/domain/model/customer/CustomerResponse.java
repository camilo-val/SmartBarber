package com.smartbarber.domain.model.customer;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CustomerResponse(
        UUID id,
        String name,
        String phone
) {
}
