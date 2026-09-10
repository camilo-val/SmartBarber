package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.domain.model.barbershop.Barbershop;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarbershopEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BarbershopAdapterMapper {
    BarbershopEntity toEntity(Barbershop barbershop);
    default Barbershop toDomain(BarbershopEntity entity) {
        if (entity == null) {
            return null;
        }

        return Barbershop.rebuild(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getUbicacion(),
                entity.getCelular(),
                entity.getDocumento(),
                mapTipoDocumento(entity.getTipoDocumento()),
                entity.getRazonSocial(),
                entity.getEstado(),
                entity.getFechaCreacion(),
                entity.getFechaModificacion()
        );
    }

    default DocumentType mapTipoDocumento(String tipoDocumento) {
        return tipoDocumento == null
                ? null
                : DocumentType.valueOf(tipoDocumento);
    }}
