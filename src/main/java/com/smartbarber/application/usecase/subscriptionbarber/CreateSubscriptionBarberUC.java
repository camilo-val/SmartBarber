package com.smartbarber.application.usecase.subscriptionbarber;

import com.smartbarber.application.command.SubscriptionBarbershopCommand;
import com.smartbarber.application.port.SubscriptionBarbershopRepositoryPort;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.subscriptionbarber.SubscriptionBarberMessageExceptions;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class CreateSubscriptionBarberUC {

    private final SubscriptionBarbershopRepositoryPort port;

    public Mono<SubscriptionBarbershop> createSubscription(SubscriptionBarbershopCommand subscriptionBarbershop) {
        System.out.println("Creating subscription barber: " + subscriptionBarbershop);
        return port.existsByOrderId(subscriptionBarbershop.orderId())
                .flatMap( exists -> {
                    System.out.println("Creating subscription barber1: " + exists);
                    if (exists) {
                        return Mono.error(() -> new BusinessExceptions(SubscriptionBarberMessageExceptions.SUBSCRIPTION_BARBER_ALREADY_EXISTS));
                    }
                    SubscriptionBarbershop newSubscription = SubscriptionBarbershop.create(subscriptionBarbershop.barberId(),
                            subscriptionBarbershop.subscriptionId(), subscriptionBarbershop.orderId(),
                            subscriptionBarbershop.transactionId() ,subscriptionBarbershop.amount(),
                            subscriptionBarbershop.duration()
                    );
                    return port.save(newSubscription);
                });
    }
}
