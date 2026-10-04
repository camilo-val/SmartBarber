package com.smartbarber.application.usecase.service;

import com.smartbarber.domain.port.ServicePort;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.service.ServiceMessageExceptions;
import com.smartbarber.domain.model.service.Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateServiceUC {

    private final ServicePort servicePort;

    public Mono<Service> crearServicio(Service service) {
        return servicePort.existByName(service.getName())
                .flatMap(exist -> {

                    if (Boolean.TRUE.equals(exist)){
                        return Mono.error(new BusinessExceptions(
                                ServiceMessageExceptions.SERVICE_EXISTENTE
                        ));
                    }

                    return Mono.just(service);
                })
                .flatMap(servicePort::save);
    }
}
