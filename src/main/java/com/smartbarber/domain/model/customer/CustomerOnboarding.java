package com.smartbarber.domain.model.customer;

import com.smartbarber.application.command.in.customer.CustomerCommand;
import com.smartbarber.application.command.in.customer.CustomerOnboardingCommand;
import com.smartbarber.application.command.in.user.UserCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.customer.CustomerMessageExceptions;

public class CustomerOnboarding {
    private final CustomerCommand customer;
    private final UserCommand  user;

    private CustomerOnboarding(CustomerCommand customer, UserCommand user) {
        this.customer = customer;
        this.user = user;
    }
    public static CustomerOnboarding create(CustomerCommand customer, UserCommand user) {
        if (customer == null || user == null) {
            throw new BusinessExceptions(CustomerMessageExceptions.INVALID_DATA);
        }
        return new CustomerOnboarding(customer, user);
    }

    public CustomerCommand getCustomer() {
        return customer;
    }

    public UserCommand getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "CustomerOnboarding{" +
                "customer=" + customer +
                ", user=" + user +
                '}';
    }
}
