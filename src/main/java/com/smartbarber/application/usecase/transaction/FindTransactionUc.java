package com.smartbarber.application.usecase.transaction;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.transaction.TransactionMessageExceptions;
import com.smartbarber.domain.model.transaction.Transaction;
import com.smartbarber.domain.port.transaction.TransactionRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
public class FindTransactionUc {
    private final TransactionRepositoryPort port;

    public Mono<Transaction> findTransactionByOrderId(UUID orderId){
        return port.findByOrderId(orderId)
                .switchIfEmpty(Mono.error(() -> new BusinessExceptions(TransactionMessageExceptions.TRANSACTION_NOT_EXISTS)));
    }

    public Mono<Transaction> findTransactionBySubscriptionId(UUID subscriptionId){
        return port.findBySubscriptionBarberId(subscriptionId)
                .switchIfEmpty(Mono.error(() -> new BusinessExceptions(TransactionMessageExceptions.TRANSACTION_NOT_EXISTS)));
    }

}
