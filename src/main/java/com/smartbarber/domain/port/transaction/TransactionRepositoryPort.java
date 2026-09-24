package com.smartbarber.domain.port.transaction;

import com.smartbarber.domain.model.transaction.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransactionRepositoryPort {
    Mono<Transaction> save(Transaction transaction);
    Mono<Transaction> findById(UUID transactionId);
    Mono<Transaction> findByOrderId(UUID orderId);
    Flux<Transaction> findByReservationId(UUID reservationId);
    Mono<Transaction> findBySubscriptionBarberId(UUID subscriptionBarberId);
    Mono<Boolean> existsByOrderId(UUID orderId);

}
