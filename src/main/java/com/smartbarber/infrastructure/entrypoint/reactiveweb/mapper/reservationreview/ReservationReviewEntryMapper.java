package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.reservationreview;

import com.smartbarber.domain.model.reservationreview.ReservationReview;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.reservationreview.ReservationReviewRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.reservationreview.ReservationReviewRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationReviewEntryMapper {
    ReservationReviewRsDto toResponse(ReservationReview reservationReview);
    default ReservationReview toDomain(ReservationReviewRqDto rqDto){
        return ReservationReview.createReservationReview(
                null,
                rqDto.reservationId(),
                rqDto.qualification(),
                rqDto.comment()
        );
    }
}
