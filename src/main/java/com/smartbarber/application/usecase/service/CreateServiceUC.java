package com.smartbarber.application.usecase.service;

import com.smartbarber.application.command.in.service.ServiceCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.service.ServiceMessageExceptions;
import com.smartbarber.domain.model.service.Service;
import com.smartbarber.domain.port.ServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreateServiceUC {

    private final ServicePort servicePort;

    public Mono<Service> createService(ServiceCommand command) {

        Service service = Service.createService(
                null,
                command.barberiaId(),
                command.name(),
                command.description(),
                command.duration(),
                command.price(),
                command.offer(),
                command.specialPrice()
        );

        return servicePort.existByNameAndDescription(
                        service.getName(),
                        service.getDescription()
                )
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(
                        new BusinessExceptions(
                                ServiceMessageExceptions.SERVICE_ALREADY_EXISTS
                        )
                ))
                .then(Mono.defer(() -> servicePort.save(service)));
    }
}