package com.smartbarber.application.usecase.customer;

import com.smartbarber.domain.model.customer.Customer;
import com.smartbarber.domain.port.CustomerPort;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.customer.CustomerMessageExceptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class SearchCustomerUC {
    private final CustomerPort customerPort;

    public Mono<Customer> findByName(String name){
        return customerPort.findByName(name)
                .doOnNext(client -> log.info("Datos encontrados {}", client))
                .switchIfEmpty(Mono.error(() -> (new BusinessExceptions(CustomerMessageExceptions.CUSTOMER_NOT_FOUD))));
    }

    public Mono<Customer> findByDocument(String document) {
        return customerPort.findByDocument(document)
                .doOnNext(client -> log.info("Datos encontrados {}", client))
                .switchIfEmpty(Mono.error(() -> (new BusinessExceptions(CustomerMessageExceptions.CUSTOMER_NOT_FOUD))));
    }

    public Mono<Customer> findById(String id){
        return customerPort.findById(UUID.fromString(id))
                .doOnNext(client -> log.info("Datos encontrados {}", client))
                .switchIfEmpty(Mono.error(() -> (new BusinessExceptions(CustomerMessageExceptions.CUSTOMER_NOT_FOUD))));
    }
}
