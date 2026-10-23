package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barbershop;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeOnboardingRsDto;
import lombok.Builder;

import java.util.UUID;

@Builder
public record BarbershopOnBoardingRsDto(
        UUID id,
        String name,
        String description,
        String location,
        String phone,
        String document,
        DocumentType documentType,
        String companyName,
        EmployeeOnboardingRsDto employee
){
}
