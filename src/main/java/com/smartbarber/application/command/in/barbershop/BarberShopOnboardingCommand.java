package com.smartbarber.application.command.in.barbershop;

import com.smartbarber.application.command.in.employee.EmployeeCommand;
import com.smartbarber.application.command.in.user.UserCommand;
import lombok.Builder;

@Builder
public record BarberShopOnboardingCommand (BarbershopCommand barbershop,
                                           UserCommand user,
                                           EmployeeCommand employee) {
}
