package com.smartbarber.application.mapper;

import com.smartbarber.application.command.in.customer.CustomerCommand;
import com.smartbarber.application.command.in.customer.CustomerOnboardingCommand;
import com.smartbarber.domain.model.customer.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerCommand toCustomerCommand(Customer customer);
}
