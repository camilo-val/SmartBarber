package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.model.reservation.Reservation;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ReservationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationAdapterMapper {
    
    ReservationEntity toEntity(Reservation reservation);
    
    default Reservation toDomain(ReservationEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Reservation.rebuild(
                entity.getId(),
                entity.getCustomerId(),
                entity.getEmployeeId(),
                entity.getReservationType(),
                entity.getStatus(),
                entity.getDuration(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getNotes(),
                entity.getCreationDate(),
                entity.getUpdatedDate()
        );
    }
}
