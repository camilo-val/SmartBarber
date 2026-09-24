package com.smartbarber.application.usecase.subscriptionbarber;

import com.smartbarber.application.command.in.SubscriptionBarbershopCommand;
import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.subscriptionbarber.SubscriptionBarberMessageExceptions;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.domain.port.subscriptionbarbershop.SubscriptionBarbershopRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RenewSubscriptionUC {
    private final SubscriptionBarbershopRepositoryPort repositoryPort;

   public  Mono<SubscriptionBarbershop> renewSubscriptionBarbershop(SubscriptionBarbershopCommand subscription){
        return repositoryPort.findByBarberId(subscription.barberId())
                .filter(barberSubscriptions ->
                        barberSubscriptions.getStatus() == SubscriptionBarberStatus.APPROVED
                                || barberSubscriptions.getStatus() == SubscriptionBarberStatus.PENDING).count()
                .flatMap(subscriptionBarbershops ->
                    subscriptionBarbershops >= 2 ?
                            Mono.error(() -> new BusinessExceptions(SubscriptionBarberMessageExceptions.THE_BARBER_HAS_ALREADY_TWO_SUBSCRIPTIONS)) :
                    repositoryPort.save(SubscriptionBarbershop.create(subscription.barberId(),
                            subscription.subscriptionId(), subscription.duration(),
                            subscription.subscriptionPrice(),subscription.subscriptionDiscount()))
                );

    }
}
