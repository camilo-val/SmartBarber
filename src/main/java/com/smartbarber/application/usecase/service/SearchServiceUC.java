package com.smartbarber.application.usecase.service;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.service.ServiceMessageExceptions;
import com.smartbarber.domain.model.service.Service;
import com.smartbarber.domain.port.ServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SearchServiceUC {

    private final ServicePort servicePort;

    public Mono<Service> findByName(String name) {
        return servicePort.findByName(name)
                .switchIfEmpty(Mono.error(
                        new BusinessExceptions(
                                ServiceMessageExceptions.SERVICE_NOT_FOUND
                        )
                ));
    }

    public Mono<Service> findById(Integer id) {
        return servicePort.findById(id)
                .switchIfEmpty(Mono.error(
                        new BusinessExceptions(
                                ServiceMessageExceptions.SERVICE_NOT_FOUND
                        )
                ));
    }
}