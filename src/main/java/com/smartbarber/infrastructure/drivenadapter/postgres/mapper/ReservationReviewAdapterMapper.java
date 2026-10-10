package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.model.reservationreview.ReservationReview;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.ReservationReviewEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel =  "spring")
public interface ReservationReviewAdapterMapper {
    ReservationReviewEntity toEntity(ReservationReview reservationReview);
    default ReservationReview toDomain(ReservationReviewEntity entity){
        if (entity == null){
            return null;
        }

        return ReservationReview.rebuild(
                entity.getId(),
                entity.getReservationId(),
                entity.getQualification(),
                entity.getComment(),
                entity.getCreateAt(),
                entity.getUpdateAt()
        );
    }
}
