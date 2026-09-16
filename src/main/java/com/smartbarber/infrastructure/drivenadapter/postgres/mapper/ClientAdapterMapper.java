package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.domain.model.client.Client;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel =  "spring")
public interface ClientAdapterMapper {
    ClientEntity toEntity(Client client);
    default Client toDomain(ClientEntity entity){
        if (entity == null){
            return null;
        }

        return Client.rebuild(
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
