package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.scheduler;

import com.smartbarber.application.usecase.schedule.CreateScheduleUC;
import com.smartbarber.application.usecase.schedule.SearchScheduleUC;
import com.smartbarber.application.usecase.schedule.UpdateScheduleUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.schedule.ScheduleRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import com.smartbarber.infrastructure.entrypoint.utils.validateRequest;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.schedule.ScheduleEntryMapper;
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
public class SchedulerHandler {
    private final ScheduleEntryMapper mapper;
    private final CreateScheduleUC createScheduleUC;
    private final validateRequest validateRequest;
    private final UpdateScheduleUC updateScheduleUC;
    private final SearchScheduleUC searchScheduleUC;

    public Mono<ServerResponse> createSchedule(ServerRequest request){
        return request.bodyToMono(ScheduleRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap(createScheduleUC::crearHorario)
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.created(request.uri()).bodyValue(response))
                .switchIfEmpty(Mono.error(new TechnicalExceptions(TechnicalMessageExceptions.BAD_REQUEST)));
    }

    public Mono<ServerResponse> findScheduleById(ServerRequest request){
        return searchScheduleUC.buscarPorId(Integer.valueOf(request.pathVariable("id")))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findScheduleByEmpleadoId(ServerRequest request){
        return searchScheduleUC.buscarPorIdEmpleado(UUID.fromString(request.pathVariable("empleadoId")))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateSchedule(ServerRequest request){
        return request.bodyToMono(ScheduleRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap(schedule -> updateScheduleUC
                        .scheduleUpdate(request.pathVariable("id"), schedule))
                        .map(mapper::toResponse)
                        .flatMap( response -> ServerResponse.accepted().bodyValue(response));
    }
}
