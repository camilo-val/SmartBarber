package com.smartbarber.domain.port;

import com.smartbarber.domain.model.barbershop.Barbershop;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BarberShopRepositoryPort {
    Mono<Barbershop> findById(UUID id);
    Mono<Barbershop> findByName(String name);
    Flux<Barbershop> findByCompanyName(String companyName);
    Mono<Barbershop> findByDocument(String document);
    Mono<Barbershop> save(Barbershop barbershop);
    Mono<Barbershop> update(UUID id, Barbershop barbershop);
    Mono<Void> deleteBarbershop(UUID id);
    Mono<Boolean> existByName(String name);
}
