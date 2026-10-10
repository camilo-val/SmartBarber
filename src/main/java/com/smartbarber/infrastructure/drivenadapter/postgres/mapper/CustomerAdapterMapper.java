package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.domain.model.customer.Customer;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel =  "spring")
public interface CustomerAdapterMapper {
    CustomerEntity toEntity(Customer customer);
    default Customer toDomain(CustomerEntity entity){
        if (entity == null){
            return null;
        }

        return Customer.rebuild(
                entity.getId(),
                entity.getUserId(),
                entity.getDocument(),
                mapTipoDocumento(entity.getDocumentType()),
                entity.getName(),
                entity.getCell(),
                entity.getEmail(),
                entity.getCreateAt(),
                entity.getUpdateAt()
        );
    }
    default DocumentType mapTipoDocumento(String documentType) {
        return documentType == null
                ? null
                : DocumentType.valueOf(documentType);
}}
