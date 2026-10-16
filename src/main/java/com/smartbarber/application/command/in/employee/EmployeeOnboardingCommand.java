package com.smartbarber.application.command.in.employee;

import com.smartbarber.application.command.in.user.UserCommand;
import lombok.Builder;


@Builder
public record EmployeeOnboardingCommand(
        EmployeeCommand employee,
        UserCommand user
) {
}
