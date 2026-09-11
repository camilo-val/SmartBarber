package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.application.port.subscriptionbarbershop.SubscriptionBarbershopRepositoryPort;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.SubscriptionBarbershopData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.SubscriptionBarbershopAdapterMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@AllArgsConstructor
@Component
public class SubscriptionBarbershopAdapter implements SubscriptionBarbershopRepositoryPort {

    private final SubscriptionBarbershopData data;
    private final SubscriptionBarbershopAdapterMapper mapper;

    @Override
    public Mono<SubscriptionBarbershop> findByOrderId(UUID id) {
        return data.findByOrderId(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByOrderId(UUID orderId) {
        System.out.println(orderId);
        return data.existsByOrderId(orderId).doOnSuccess(x -> System.out.println("onSuccess: " +x))
                .doOnError(x -> System.out.println("onError: " +x));
    }

    @Override
    public Flux<SubscriptionBarbershop> findByBarberId(UUID barberId) {
        return data.findByBarberId(barberId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<SubscriptionBarbershop> findByTransactionId(UUID transactionId) {
        return data.findByTransactionId(transactionId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<SubscriptionBarbershop> save(SubscriptionBarbershop subscriptionBarbershop) {
        return data.save(mapper.toEntity(subscriptionBarbershop))
                .map(mapper::toDomain).doOnSuccess(x -> System.out.println("onSuccess: " +x))
                .doOnError(x -> System.out.println("onError: " +x));
    }

    @Override
    public Mono<SubscriptionBarbershop> update(SubscriptionBarbershop subscriptionBarbershop) {
        return data.save(mapper.toEntity(subscriptionBarbershop))
                .map(mapper::toDomain);
    }
}
