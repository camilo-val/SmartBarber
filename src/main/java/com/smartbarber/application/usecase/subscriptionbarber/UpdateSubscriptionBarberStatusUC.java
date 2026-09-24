package com.smartbarber.application.usecase.subscriptionbarber;

import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.domain.port.subscriptionbarbershop.SubscriptionBarbershopRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UpdateSubscriptionBarberStatusUC {
    private final SubscriptionBarbershopRepositoryPort port;

    public Mono<SubscriptionBarbershop> changeStatus(SubscriptionBarberStatus status, UUID subscriptionId){
        return port.findById(subscriptionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Subscription not found")))
                .flatMap(subscription -> {
                    SubscriptionBarbershop subscriptionBarbershop = subscription.processPaymentResult(status);
                    return port.save(subscriptionBarbershop);
                });

    }
}
