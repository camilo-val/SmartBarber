package com.smartbarber.application.usecase.barbershop;

import com.smartbarber.application.command.in.barbershop.BarbershopCommand;
import com.smartbarber.domain.port.BarberShopRepositoryPort;
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

    public Mono<Barbershop> createBarbershop(BarbershopCommand barbershop) {
        return existBarbershop(barbershop)
                .flatMap(exist -> {
                    if(Boolean.TRUE.equals(exist)){
                        return Mono.error(() -> new BusinessExceptions(BarberShopMessageExceptions.BARBERSHOP_ALREADY_EXIST));
                    }
                    return Mono.just(Barbershop.createBarbershop(barbershop.name(),
                            barbershop.description(),
                            barbershop.location(),
                            barbershop.phone(),
                            barbershop.document(),
                            barbershop.documentType(),
                            barbershop.companyName()));
                })
                .flatMap(barberShopRepositoryPort::save);
    }

    private Mono<Boolean> existBarbershop(BarbershopCommand barbershop) {
        return Mono.zip(
                barberShopRepositoryPort.existByName(barbershop.name()),
                barberShopRepositoryPort.existByDocument(barbershop.document())
        ).map(tuple -> tuple.getT1() || tuple.getT2())
                .flatMap(exist -> {
                    barberShopRepositoryPort.existByCompanyName(barbershop.companyName());
                    return barbershop.companyName() == null || barbershop.companyName().isEmpty()
                            ? Mono.just(exist)
                            : barberShopRepositoryPort.existByCompanyName(barbershop.companyName())
                            .map(exist1 -> exist || exist1);
                });
    }
}
