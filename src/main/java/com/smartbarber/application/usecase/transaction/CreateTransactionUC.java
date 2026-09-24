package com.smartbarber.application.usecase.transaction;

import com.smartbarber.application.command.in.MessageCommand;
import com.smartbarber.domain.model.transaction.Transaction;
import com.smartbarber.domain.port.event.MessageNotificationPort;
import com.smartbarber.domain.port.transaction.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateTransactionUC {
    private final TransactionRepositoryPort repositoryPort;
    private static final String CURRENCY = "COP";
    private static final String DESCRIPTION = "Transaction for subscription: ";
    private final MessageNotificationPort messagePort;

    public Mono<Transaction> createAndSendTransaction(UUID subscriptionId, BigInteger amount, Byte discount){
        return repositoryPort.save(Transaction.
                createSubscriptionTransaction(subscriptionId,amount,discount))
                .flatMap( transaction -> {
                    MessageCommand message = MessageCommand.builder()
                            .amount(transaction.getAmount())
                            .currency(CURRENCY)
                            .description(DESCRIPTION + subscriptionId)
                            .orderId(transaction.getOrderId())
                            .build();
                    return messagePort.notification(message)
                            .thenReturn(transaction);
                });
    }
}
