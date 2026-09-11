package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.domain.model.barbershop.Barbershop;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarbershopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BarbershopAdapterMapper {
    BarbershopEntity toEntity(Barbershop barbershop);
    default Barbershop toDomain(BarbershopEntity entity) {
        if (entity == null) {
            return null;
        }

        return Barbershop.rebuild(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getLocation(),
                entity.getPhone(),
                entity.getDocument(),
                mapTipoDocumento(entity.getDocumentType()),
                entity.getCompanyName(),
                entity.getStatus(),
                entity.getCreateAt(),
                entity.getUpdateAt()
        );
    }

    default DocumentType mapTipoDocumento(String tipoDocumento) {
        return tipoDocumento == null
                ? null
                : DocumentType.valueOf(tipoDocumento);
    }}
