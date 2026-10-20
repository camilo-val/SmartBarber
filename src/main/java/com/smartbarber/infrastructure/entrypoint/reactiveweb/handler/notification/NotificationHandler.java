package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.notification;

import com.smartbarber.application.usecase.notification.CreateNotificationUC;
import com.smartbarber.application.usecase.notification.SearchNotificationUC;
import com.smartbarber.application.usecase.notification.UpdateNotificationUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.notification.NotificationRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.notification.NotificationEntryMapper;
import com.smartbarber.infrastructure.entrypoint.utils.validateRequest;
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
public class NotificationHandler {

    private final NotificationEntryMapper mapper;
    private final CreateNotificationUC createNotificationUC;
    private final validateRequest validateRequest;
    private final UpdateNotificationUC updateNotificationUC;
    private final SearchNotificationUC searchNotificationUC;

    public Mono<ServerResponse> createNotification(ServerRequest request) {
        return request.bodyToMono(NotificationRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toCommand)
                .flatMap(createNotificationUC::createNotification)
                .map(mapper::toResponse)
                .flatMap(response ->
                        ServerResponse.created(request.uri()).bodyValue(response))
                .switchIfEmpty(Mono.error(
                        new TechnicalExceptions(TechnicalMessageExceptions.BAD_REQUEST)));
    }

    public Mono<ServerResponse> findNotificationById(ServerRequest request) {
        return Mono.fromCallable(() ->
                        UUID.fromString(request.pathVariable("id")))
                .flatMap(searchNotificationUC::findById)
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> findNotificationsByReservationId(ServerRequest request) {
        return Mono.fromCallable(() ->
                        UUID.fromString(request.pathVariable("reservationId")))
                .flatMapMany(searchNotificationUC::findByReservationId)
                .map(mapper::toResponse)
                .collectList()
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> findNotificationsByCustomerId(ServerRequest request) {
        return Mono.fromCallable(() ->
                        UUID.fromString(request.pathVariable("customerId")))
                .flatMapMany(searchNotificationUC::findByCustomerId)
                .map(mapper::toResponse)
                .collectList()
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> updateNotification(ServerRequest request) {
        return Mono.fromCallable(() ->
                        UUID.fromString(request.pathVariable("id")))
                .flatMap(id -> request.bodyToMono(NotificationRqDto.class)
                        .doOnNext(validateRequest::validate)
                        .map(mapper::toCommand)
                        .flatMap(notification ->
                                updateNotificationUC.updateNotification(id, notification)))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.accepted().bodyValue(response));
    }
}
