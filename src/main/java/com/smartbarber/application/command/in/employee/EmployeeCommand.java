package com.smartbarber.application.command.in.employee;

import com.smartbarber.domain.enums.DocumentType;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record EmployeeCommand (
        UUID id,
        UUID userId,
        UUID barberiaId,
        String document,
        DocumentType documentType,
        String name,
        String cell,
        String email,
        String specialty,
        Instant createAt,
        Instant updateAt
){
}
