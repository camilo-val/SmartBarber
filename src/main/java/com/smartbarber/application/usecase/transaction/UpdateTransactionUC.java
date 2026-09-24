package com.smartbarber.application.usecase.transaction;

import com.smartbarber.domain.enums.TransactionStatus;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.transaction.TransactionMessageExceptions;
import com.smartbarber.domain.model.transaction.Transaction;
import com.smartbarber.domain.port.transaction.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UpdateTransactionUC {
    private final TransactionRepositoryPort port;

    public Mono<Transaction> changeStatus(TransactionStatus status, UUID orderId){
        return port.findByOrderId(orderId)
                .switchIfEmpty(Mono.error(() ->
                        new BusinessExceptions(TransactionMessageExceptions.TRANSACTION_NOT_EXISTS)))
                .flatMap(transaction -> {
                    Transaction updatedTransaction = transaction.processTransaction(status);
                    return port.save(updatedTransaction);
                });

    }
}
