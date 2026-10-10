package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User.UserRsDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.UUID;
@Builder
public record EmployeeOnboardingRsDto(
        String name,
        String document,
        DocumentType documentType,
        String cell,
        String email,
        String specialty,
        UserRsDto user
    ){

}
