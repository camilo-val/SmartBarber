package com.smartbarber.domain.port;

import com.smartbarber.domain.model.customer.Customer;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CustomerPort {
    Mono<Customer> findById(UUID id);
    Mono<Customer> findByName(String name);
    Mono<Customer> findByDocument(String document);
    Mono<Customer> save(Customer customer);
    Mono<Customer> update(UUID id, Customer customer);
    Mono<Void> eliminarCliente(UUID id);
    Mono<Boolean> existsByDocument(String document);
    Mono<Customer> findByUserId(UUID userId);
}
