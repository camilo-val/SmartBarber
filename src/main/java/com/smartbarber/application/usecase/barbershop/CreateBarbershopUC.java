package com.smartbarber.application.usecase.barbershop;

import com.smartbarber.application.port.BarberShopRepositoryPort;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.barbershop.BarberShopMessageExceptions;
import com.smartbarber.domain.model.barbershop.Barbershop;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateBarbershopUC {
    private final BarberShopRepositoryPort barberShopRepositoryPort;

    public Mono<Barbershop> crearBarberia(Barbershop barbershop) {
        return barberShopRepositoryPort.existByName(barbershop.getName())
                .flatMap(exist -> {
                    if(Boolean.TRUE.equals(exist)){
                        return Mono.error(new BusinessExceptions(BarberShopMessageExceptions.BARBERSHOP_ALREADY_EXIST));
                    }
                    return Mono.just(barbershop);
                })
                .flatMap(barberShopRepositoryPort::save);
    }
}
