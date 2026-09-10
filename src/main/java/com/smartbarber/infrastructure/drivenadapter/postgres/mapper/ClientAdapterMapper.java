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

        return Client.reconstruir(
                entity.getId(),
                entity.getUserId(),
                entity.getDocumento(),
                mapTipoDocumento(entity.getTipoDocumento()),
                entity.getNombre(),
                entity.getCelular(),
                entity.getCorreo(),
                entity.getFechaCreacion(),
                entity.getFechaModificacion()
        );
    }
    default DocumentType mapTipoDocumento(String tipoDocumento) {
        return tipoDocumento == null
                ? null
                : DocumentType.valueOf(tipoDocumento);
}}
