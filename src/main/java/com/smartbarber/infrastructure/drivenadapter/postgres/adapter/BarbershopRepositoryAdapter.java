package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.application.port.BarberShopRepositoryPort;
import com.smartbarber.domain.model.barbershop.Barbershop;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.BarbershopData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.BarbershopAdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class BarbershopRepositoryAdapter implements BarberShopRepositoryPort {
    private final BarbershopData barbershopData;
    private final BarbershopAdapterMapper mapper;
    @Override
    public Mono<Barbershop> findById(UUID id) {
        return barbershopData.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Barbershop> findByName(String name) {
        return barbershopData.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Barbershop> findByCompanyName(String razonSocial) {
        return barbershopData.findByCompanyName(razonSocial)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Barbershop> findByDocument(String document) {
        return barbershopData.findByDocument(document)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Barbershop> save(Barbershop barbershop) {
        System.out.println("BARBERSHOP SAVING:   " +  barbershop );
        System.out.println("BARBERSHOP ENTITy: " + mapper.toEntity(barbershop).toString());
        return barbershopData.save(mapper.toEntity(barbershop))
                .doOnNext(e -> log.info("Data registrada {}", e.toString()))
                .doOnSuccess(entityGuardada ->
                        log.info("Proceso de guardado finalizado correctamente"))
                .doOnError(error ->
                        log.error("Error guardando barbería", error))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Barbershop> update(UUID id, Barbershop barbershop) {
        return barbershopData.save(mapper.toEntity(barbershop))
                .map(mapper::toDomain);    }

    @Override
    public Mono<Void> deleteBarbershop(UUID id) {
        return barbershopData.deleteById(id);
    }

    @Override
    public Mono<Boolean> existByName(String nombreBarberia) {
        return barbershopData.findByName(nombreBarberia)
                .map(mapper::toDomain).hasElement();
    }
}
