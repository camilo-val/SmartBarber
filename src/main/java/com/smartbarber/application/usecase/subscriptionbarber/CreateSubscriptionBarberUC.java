package com.smartbarber.application.usecase.subscriptionbarber;

import com.smartbarber.application.command.in.SubscriptionBarbershopCommand;
import com.smartbarber.application.usecase.transaction.CreateTransactionUC;
import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import com.smartbarber.domain.model.suscription.Subscription;
import com.smartbarber.domain.port.subscriptionbarbershop.SubscriptionBarbershopRepositoryPort;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.subscriptionbarber.SubscriptionBarberMessageExceptions;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Log4j2
@AllArgsConstructor
@Component
public class CreateSubscriptionBarberUC {

    private final SubscriptionBarbershopRepositoryPort port;
    private final CreateTransactionUC transactionUC;

    public Mono<SubscriptionBarbershop> createSubscription(SubscriptionBarbershopCommand subscriptionBarbershop, Subscription plan) {
        return port.findByBarberId(subscriptionBarbershop.barberId())
                .filter(exists -> exists.getStatus() == SubscriptionBarberStatus.APPROVED
                        || exists.getStatus() == SubscriptionBarberStatus.PENDING)
                .count()
                .flatMap(count -> {
                    if (count > 2) {
                        return Mono.error(() ->
                                new BusinessExceptions(SubscriptionBarberMessageExceptions.THE_BARBER_HAS_ALREADY_TWO_SUBSCRIPTIONS));
                    }
                    SubscriptionBarbershop newSubscription = SubscriptionBarbershop
                            .create(subscriptionBarbershop.barberId(), subscriptionBarbershop.subscriptionId(),subscriptionBarbershop.duration(),
                                    plan.getPrice(), plan.getDiscount()
                            );
                    return port.save(newSubscription)
                            .flatMap( subscription -> transactionUC.createAndSendTransaction(subscription.getId(), subscription.getSubscriptionPrice(), subscription.getSubscriptionDiscount())
                            .thenReturn(subscription));
                });
    }

    public Mono<SubscriptionBarbershop> updateStatusSubscription(UUID id, SubscriptionBarberStatus status) {
        return port.findById(id)
                .onErrorResume(BusinessExceptions.class,e ->
                        Mono.error(() ->
                                new BusinessExceptions(SubscriptionBarberMessageExceptions.SUBSCRIPTION_BARBER_NOT_FOUND)
                        ))
                .flatMap(subscriptionExist -> {
                    SubscriptionBarbershop processSubscription = subscriptionExist.processPaymentResult(status);
                    return port.save(processSubscription);
                });
    }
}
