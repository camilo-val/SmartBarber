package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.employee;

import com.smartbarber.application.usecase.Employee.UpdateEmployeeUC;
import com.smartbarber.application.usecase.Employee.CrateEmployeeUC;
import com.smartbarber.application.usecase.Employee.SearchEmployeeUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.employee.EmployeeEntryMapper;
import com.smartbarber.infrastructure.entrypoint.utils.validateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class EmployeeHandler {
    private final EmployeeEntryMapper mapper;
    private final CrateEmployeeUC crateEmployeeUC;
    private final  validateRequest validateRequest;
    private final UpdateEmployeeUC updateEmployeeUC;
    private final SearchEmployeeUC searchEmployeeUC;

    public Mono<ServerResponse> crearEmpleado(ServerRequest request){
        return request.bodyToMono(EmployeeRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap(crateEmployeeUC::crearEmpleado)
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.created(request.uri()).bodyValue(response))
                .switchIfEmpty(Mono.error(new TechnicalExceptions(TechnicalMessageExceptions.BAD_REQUEST)));
    }

    public Mono<ServerResponse> buscarEmpleadoPorNombre(ServerRequest request){
        return searchEmployeeUC.buscarPorNombre(request.pathVariable("nombre"))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> buscarEmpleadoPorDocumento(ServerRequest request){
        return searchEmployeeUC.buscarPorDocuemnto(request.pathVariable("documento"))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> buscarEmpleadoPorId(ServerRequest request){
        return searchEmployeeUC.buscarPorId(request.pathVariable("id"))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> actualizarEmpleado(ServerRequest request){
        return request.bodyToMono(EmployeeRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap(employee -> updateEmployeeUC
                        .actualizarEmpleado(request.pathVariable("id"), employee))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.accepted().bodyValue(response));
    }
}
