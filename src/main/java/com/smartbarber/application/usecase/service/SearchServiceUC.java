package com.smartbarber.application.usecase.service;

import com.smartbarber.domain.port.ServicePort;
import com.smartbarber.domain.exceptions.service.ServiceMessageExceptions;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.model.service.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class SearchServiceUC {
    private final ServicePort servicePort;

    public Mono<Service> buscarPorNombre(String name){
        return servicePort.findByName(name)
                .doOnNext(service -> log.info("Datos encontrados {}", service))
                .switchIfEmpty(Mono.error(new BusinessExceptions(ServiceMessageExceptions.SERVICE_NO_EXISTE)));
    }
    public Mono<Service> buscarPorId(Integer id){
        return servicePort.findById(id)
                .doOnNext(service -> log.info("Datos encontrados {}", service))
                .switchIfEmpty(Mono.error(new BusinessExceptions(ServiceMessageExceptions.SERVICE_NO_EXISTE)));
    }
}
