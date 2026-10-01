package com.smartbarber.application.usecase.subscriptionbarber;

import com.smartbarber.application.command.in.SubscriptionBarbershopCommand;
import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.domain.port.subscriptionbarbershop.SubscriptionBarbershopRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Instant;

@RequiredArgsConstructor
@Component
public class ExpiredSubscriptionUc {

    private final SubscriptionBarbershopRepositoryPort port;

    public Flux<SubscriptionBarbershop> expiredSubscription(SubscriptionBarbershopCommand subscriptionBarbershopCommand){
        return port.findByBarberId(subscriptionBarbershopCommand.barberId()).
                filter(subscription -> subscription.getStatus() == SubscriptionBarberStatus.ACTIVE)
                .flatMap(subscriptionExpired ->
                    port.save(subscriptionExpired.processPaymentResult(SubscriptionBarberStatus.EXPIRED))
                );
    }
}
