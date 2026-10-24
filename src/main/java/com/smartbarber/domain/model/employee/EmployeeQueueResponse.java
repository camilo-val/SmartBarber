package com.smartbarber.domain.model.employee;

import lombok.Builder;

import java.util.UUID;

@Builder
public record EmployeeQueueResponse(UUID id, String name) {
}
