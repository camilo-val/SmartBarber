package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.model.transaction.Transaction;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionAdapterMapper {

    default Transaction toDomain(TransactionEntity transactionEntity){
        if(transactionEntity == null){
            return null;
        }
        return Transaction.rebuild(
            transactionEntity.getTransactionId(),
            transactionEntity.getReservationId(),
            transactionEntity.getSubscriptionBarberId(),
            transactionEntity.getAmount(),
            transactionEntity.getDiscount(),
            transactionEntity.getSubtotal(),
            transactionEntity.getStatus(),
            transactionEntity.getOrderId(),
            transactionEntity.getCreatedAt(),
            transactionEntity.getUpdatedAt()
        );
    }

    TransactionEntity toEntity(Transaction transaction);
}
