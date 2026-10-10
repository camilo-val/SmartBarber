package com.smartbarber.application.usecase.customer;

import com.smartbarber.application.command.in.customer.CustomerCommand;
import com.smartbarber.domain.model.customer.Customer;
import com.smartbarber.domain.port.CustomerPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdateCustomerUC {
    private final CustomerPort customerPort;

    public Mono<Customer> customerUpdate(String id, CustomerCommand customer){

        return customerPort.findById(UUID.fromString(id))
                .map( clientDB -> Customer.update(clientDB.getId(), clientDB.getUserId(), customer.document(),
                        customer.documentType(), customer.name(), customer.cell(), customer.email(),
                        clientDB.getCreateAt()))
                .flatMap( clientUpdate -> customerPort.update(UUID.fromString(id), clientUpdate));
    }
}
