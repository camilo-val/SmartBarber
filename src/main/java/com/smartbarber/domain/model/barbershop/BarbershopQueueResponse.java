package com.smartbarber.domain.model.barbershop;

import com.smartbarber.domain.model.employee.EmployeeQueueResponse;
import lombok.Builder;

import java.util.UUID;

@Builder
public record BarbershopQueueResponse(UUID id,
                                      String name,
                                      String location,
                                      EmployeeQueueResponse employee) {

}
