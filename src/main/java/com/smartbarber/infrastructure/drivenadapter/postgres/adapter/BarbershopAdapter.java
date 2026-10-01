package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.port.BarberShopRepositoryPort;
import com.smartbarber.domain.model.barbershop.Barbershop;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.BarbershopData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.BarbershopAdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class BarbershopAdapter implements BarberShopRepositoryPort {
    private final BarbershopData barbershopData;
    private final BarbershopAdapterMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Mono<Barbershop> findById(UUID id) {
        return barbershopData.findById(id)
                .map(mapper::toDomain);
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Barbershop> findByName(String name) {
        return barbershopData.findByName(name)
                .map(mapper::toDomain);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Barbershop> findByCompanyName(String razonSocial) {
        return barbershopData.findByCompanyName(razonSocial)
                .map(mapper::toDomain);
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Barbershop> findByDocument(String document) {
        return barbershopData.findByDocument(document)
                .map(mapper::toDomain);
    }

    @Transactional
    @Override
    public Mono<Barbershop> save(Barbershop barbershop) {
        return barbershopData.save(mapper.toEntity(barbershop))
                .map(mapper::toDomain);
    }

    @Transactional
    @Override
    public Mono<Barbershop> update(UUID id, Barbershop barbershop) {
        return barbershopData.save(mapper.toEntity(barbershop))
                .map(mapper::toDomain);    }

    @Transactional
    @Override
    public Mono<Void> deleteBarbershop(UUID id) {
        return barbershopData.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Boolean> existByName(String nombreBarberia) {
        return barbershopData.existsByName(nombreBarberia);
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Boolean> existByCompanyName(String companyName) {
        return barbershopData.existsByCompanyName(companyName);
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Boolean> existByDocument(String document) {
        return barbershopData.existsByDocument(document);
    }
}
