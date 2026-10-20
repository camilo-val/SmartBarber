package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.reservationreview;

import com.smartbarber.application.command.in.reservationreview.ReservationReviewCommand;
import com.smartbarber.domain.model.reservationreview.ReservationReview;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.reservationreview.ReservationReviewRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.reservationreview.ReservationReviewRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationReviewEntryMapper {

    ReservationReviewRsDto toResponse(ReservationReview reservationReview);

    default ReservationReviewCommand toCommand(
            ReservationReviewRqDto rqDto
    ) {
        return ReservationReviewCommand.builder()
                .reservationId(rqDto.reservationId())
                .qualification(rqDto.qualification())
                .comment(rqDto.comment())
                .build();
    }
}