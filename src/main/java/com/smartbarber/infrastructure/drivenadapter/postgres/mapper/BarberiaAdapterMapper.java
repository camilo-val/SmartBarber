package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.enums.TipoDocumento;
import com.smartbarber.domain.model.barberia.Barberia;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarberiaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BarberiaAdapterMapper {
    BarberiaEntity toEntity(Barberia barberia);
    default Barberia toDomain(BarberiaEntity entity) {
        if (entity == null) {
            return null;
        }

        return Barberia.reconstruir(
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

    default TipoDocumento mapTipoDocumento(String tipoDocumento) {
        return tipoDocumento == null
                ? null
                : TipoDocumento.valueOf(tipoDocumento);
    }}
