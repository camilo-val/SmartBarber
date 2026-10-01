package com.smartbarber.application.usecase.subscriptionbarber;

import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.subscriptionbarber.SubscriptionBarberMessageExceptions;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.domain.port.subscriptionbarbershop.SubscriptionBarbershopRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class UpdateSubscriptionBarberStatusUC {
    private final SubscriptionBarbershopRepositoryPort port;

    public Mono<SubscriptionBarbershop> changeStatus(SubscriptionBarbershop subscriptionBarbershop,SubscriptionBarberStatus status){
        return  port.countActiveSubscriptionsByBarberId(subscriptionBarbershop.getBarberId())
                .flatMap(subscriptionsActive -> {
                    SubscriptionBarberStatus newStatus = status;
                    System.out.println("UpdateSubscriptionBarberStatusUC.changeStatus: subscriptionsActive = " + subscriptionBarbershop);
                    if (Boolean.TRUE.equals(subscriptionsActive) && status == SubscriptionBarberStatus.ACTIVE){
                        newStatus = SubscriptionBarberStatus.APPROVED;
                    }
                    SubscriptionBarbershop changeSubscription = subscriptionBarbershop.processPaymentResult(newStatus);

                    return processSubscription(changeSubscription);
                });

    }
    private Mono<SubscriptionBarbershop> processSubscription(SubscriptionBarbershop subscriptionBarbershop) {
        return port.findById(subscriptionBarbershop.getId())
                        .flatMap(subscription ->
                                port.save(subscription.processPaymentResult(subscriptionBarbershop.getStatus())));

    }
}
