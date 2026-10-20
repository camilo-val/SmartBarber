package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.model.customer.Customer;
import com.smartbarber.domain.port.CustomerPort;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.CustomerData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.CustomerAdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class CustomerAdapter implements CustomerPort {
    private final CustomerData customerData;
    private final CustomerAdapterMapper mapper;
    @Override
    public Mono<Customer> findById(UUID id){
        return customerData.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Customer> findByName(String name){
        return customerData.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Customer> findByDocument(String document){
        return customerData.findByDocument(document)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Customer> save(Customer customer){
        System.out.println("CustomerAdapter.save customer: " + customer);
        return customerData.save(mapper.toEntity(customer))
                .doOnNext( e -> log.info("Data registrada {}", e.toString()))
                .doOnSuccess( entityGuardada ->
                        log.info("Proceso de guardado finalizado correctamente"))
                .doOnError( error ->
                        log.error("Error guardando cliente", error))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Customer> update(UUID id, Customer customer){
        return customerData.save(mapper.toEntity(customer))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> eliminarCliente(UUID id) { return customerData.deleteById(id);}

    @Override
    public Mono<Boolean> existsByDocument(String document) {
        return customerData.existsByDocument(document);
    }

    @Override
    public Mono<Customer> findByUserId(UUID userId) {
        return customerData.findByUserId(userId)
                .map(mapper::toDomain);
    }
}
