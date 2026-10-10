package com.smartbarber.application.usecase.barbershop;

import com.smartbarber.application.command.in.barbershop.BarbershopCommand;
import com.smartbarber.domain.port.BarberShopRepositoryPort;
import com.smartbarber.domain.model.barbershop.Barbershop;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Component
@AllArgsConstructor
public class BarbershopUpdateUC {
    private final BarberShopRepositoryPort barberShopRepositoryPort;

    public Mono<Barbershop> barberUpdate(String id, BarbershopCommand barbershop){

        return barberShopRepositoryPort.findById(UUID.fromString(id))
                .map(existsBarber -> Barbershop.update(existsBarber.getId(), barbershop.name(), barbershop.description(),
                        barbershop.location(), barbershop.phone(), barbershop.document(), barbershop.documentType(), barbershop.companyName()
                , barbershop.status(), existsBarber.getCreateAt(), Instant.now()))
                .flatMap(barberUpdate -> barberShopRepositoryPort.update(UUID.fromString(id), barberUpdate));
    }

}

