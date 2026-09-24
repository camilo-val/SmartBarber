package com.smartbarber.infrastructure.drivenadapter.websocket.mapper;

import com.smartbarber.domain.model.transaction.Transaction;
import com.smartbarber.infrastructure.drivenadapter.websocket.dto.TransactionWsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionWsAdapterMapper {
    default Transaction toDomain(TransactionWsDto dto){
        if(dto == null){
            return null;
        }
        return Transaction.rebuild(
                dto.transactionId(),
                dto.reservationId(),
                dto.subscriptionBarberId(),
                dto.amount(),
                dto.discount(),
                dto.subtotal(),
                dto.status(),
                dto.orderId(),
                dto.createdAt(),
                dto.updatedAt()
        );
    }
}
