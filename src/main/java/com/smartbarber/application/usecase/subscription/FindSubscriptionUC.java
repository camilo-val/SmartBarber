package com.smartbarber.application.usecase.subscription;

import com.smartbarber.application.port.subscription.SubscriptionRepositoryPort;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.suscription.SubscriptionMessageExceptions;
import com.smartbarber.domain.model.suscription.Subscription;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@Component
public class FindSubscriptionUC {
    private final SubscriptionRepositoryPort port;

    public Mono<Subscription> findSubscriptionById(String id){
        return port.findById(Integer.valueOf(id))
                .switchIfEmpty(Mono.error(() -> new BusinessExceptions(SubscriptionMessageExceptions.SUBSCRIPTION_NOT_FOUND)));
    }

    public Flux<Subscription> findSubscriptionAll(){
        return port.findAll()
                .switchIfEmpty(Mono.error(() -> new BusinessExceptions(SubscriptionMessageExceptions.SUBSCRIPTION_NOT_FOUND)));
    }
}
