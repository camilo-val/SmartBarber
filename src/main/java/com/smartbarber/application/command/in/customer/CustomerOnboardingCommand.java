package com.smartbarber.application.command.in.customer;

import com.smartbarber.application.command.in.user.UserCommand;
import lombok.Builder;


@Builder
public record CustomerOnboardingCommand(
        CustomerCommand customer,
        UserCommand user
) {
}
