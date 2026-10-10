package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.reservationreview;

import com.smartbarber.application.usecase.reservationreview.CreateReservationReviewUC;
import com.smartbarber.application.usecase.reservationreview.SearchReservationReviewUC;
import com.smartbarber.application.usecase.reservationreview.UpdateReservationReviewUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.reservationreview.ReservationReviewRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import com.smartbarber.infrastructure.entrypoint.utils.validateRequest;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.reservationreview.ReservationReviewEntryMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class ReservationReviewHandler {

    private final ReservationReviewEntryMapper mapper;
    private final CreateReservationReviewUC createReservationReviewUC;
    private final validateRequest validateRequest;
    private final UpdateReservationReviewUC updateReservationReviewUC;
    private final SearchReservationReviewUC searchReservationReviewUC;

    public Mono<ServerResponse> createReservationReview(ServerRequest request){
        return request.bodyToMono(ReservationReviewRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap(createReservationReviewUC::createReservationReview)
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.created(request.uri()).bodyValue(response))
                .switchIfEmpty(Mono.error(new TechnicalExceptions(TechnicalMessageExceptions.BAD_REQUEST)));
    }

    public Mono<ServerResponse> findReservationReviewById(ServerRequest request){
        return searchReservationReviewUC.buscarPorId(Integer.valueOf(request.pathVariable("id")))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findReservationReviewByReservationId(ServerRequest request){
        return searchReservationReviewUC.buscarPorIdReserva(UUID.fromString(request.pathVariable("reservationId")))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateReservationReview(ServerRequest request){
        return request.bodyToMono(ReservationReviewRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap(reservationReview -> updateReservationReviewUC
                        .reservationReviewUpdate(request.pathVariable("id"), reservationReview))
                        .map(mapper::toResponse)
                        .flatMap( response -> ServerResponse.accepted().bodyValue(response));
    }
}
