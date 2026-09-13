package com.smartbarber.application.usecase.barbershop;

import com.smartbarber.domain.port.BarberShopRepositoryPort;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.barbershop.BarberShopMessageExceptions;
import com.smartbarber.domain.model.barbershop.Barbershop;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class FindBarbershopUC {
    private final BarberShopRepositoryPort barberShopRepositoryPort;

    public Mono<Barbershop> buscarPorNombre(String nombre) {
        return barberShopRepositoryPort.findByName(nombre)
                .doOnNext(barberia -> log.info("Datos encontrados {}",  barberia))
                .switchIfEmpty(Mono.error(new BusinessExceptions(BarberShopMessageExceptions.BARBER_NOT_FOUND)));
    }

    public Flux<Barbershop> buscarPorRazonSocial(String razonSocial) {
        return barberShopRepositoryPort.findByCompanyName(razonSocial)
                .doOnNext(barberia -> log.info("Datos encontrados {}",  barberia))
                .switchIfEmpty(Mono.error(new BusinessExceptions(BarberShopMessageExceptions.BARBER_NOT_FOUND)));
    }

    public Mono<Barbershop> bucarPorDocumento(String documento) {
        return barberShopRepositoryPort.findByDocument(documento)
                .doOnNext(barberia -> log.info("Datos encontrados {}",  barberia))
                .switchIfEmpty(Mono.error(new BusinessExceptions(BarberShopMessageExceptions.BARBER_NOT_FOUND)));
    }

    public Mono<Barbershop> buscarPorId(String id) {
        return barberShopRepositoryPort.findById(UUID.fromString(id))
                .doOnNext(barberia -> log.info("Datos encontrados {}",  barberia))
                .switchIfEmpty(Mono.error(new BusinessExceptions(BarberShopMessageExceptions.BARBER_NOT_FOUND)));
    }

}
