package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.model.service.Service;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ServiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel =  "spring")
public interface ServiceAdapterMapper {
    ServiceEntity toEntity(Service service);
    default Service toDomain(ServiceEntity entity){
        if (entity == null){
            return null;
        }

        return Service.rebuild(
                entity.getId(),
                entity.getBarberiaId(),
                entity.getName(),
                entity.getDescription(),
                entity.getDuration(),
                entity.getStatus(),
                entity.getOffert(),
                entity.getPrice(),
                entity.getSpecial_price(),
                entity.getCreateAt(),
                entity.getUpdateAt()
        );
    }
}
