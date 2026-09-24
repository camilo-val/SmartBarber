package com.smartbarber.application.usecase.subscriptionbarber;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.subscriptionbarber.SubscriptionBarberMessageExceptions;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.domain.port.subscriptionbarbershop.SubscriptionBarbershopRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindSubscriptionBarberUC {
    private final SubscriptionBarbershopRepositoryPort port;

    public Flux<SubscriptionBarbershop> findSubscriptionByBarberId(UUID barberId) {
        return port.findByBarberId(barberId);
    }

    public Mono<SubscriptionBarbershop> findSubscriptionById(UUID id) {
        return port.findById(id).switchIfEmpty(Mono.error(() -> new BusinessExceptions(SubscriptionBarberMessageExceptions.SUBSCRIPTION_BARBER_NOT_FOUND)));
    }
}
