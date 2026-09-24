package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.model.transaction.Transaction;
import com.smartbarber.domain.port.transaction.TransactionRepositoryPort;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.TransactionData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.TransactionAdapterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class TransactionAdapter implements TransactionRepositoryPort {

    private final TransactionData transactionData;
    private final TransactionAdapterMapper mapper;

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        return transactionData.save(mapper.toEntity(transaction))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Transaction> findById(UUID transactionId) {
        return transactionData.findById(transactionId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Transaction> findByOrderId(UUID orderId) {
        return transactionData.findByOrderId(orderId)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Transaction> findByReservationId(UUID reservationId) {
        return transactionData.findByReservationId(reservationId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Transaction> findBySubscriptionBarberId(UUID subscriptionBarberId) {
        return transactionData.findBySubscriptionBarberId(subscriptionBarberId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByOrderId(UUID orderId) {
        return transactionData.existsByOrderId(orderId);
    }
}
