package com.smartbarber.application.usecase.subscription;

import com.smartbarber.application.command.SubscriptionCommand;
import com.smartbarber.domain.port.subscription.SubscriptionRepositoryPort;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.suscription.SubscriptionMessageExceptions;
import com.smartbarber.domain.model.suscription.Subscription;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateSubscriptionUC {

    private final SubscriptionRepositoryPort port;

    public Mono<Subscription> createSubscription(SubscriptionCommand subscription){
        return port.existsByName(subscription.name())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)){
                        return Mono.error(() -> new BusinessExceptions(SubscriptionMessageExceptions.SUBSCRIPTION_ALREADY_EXIST));
                    }
                    Subscription newSubscription =  Subscription.
                            create(subscription.name(), subscription.description(),
                                    subscription.price());
                    return port.save(newSubscription);
                });
    }
}
