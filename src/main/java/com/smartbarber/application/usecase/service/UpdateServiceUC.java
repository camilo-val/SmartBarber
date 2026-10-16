package com.smartbarber.application.usecase.service;

import com.smartbarber.application.command.in.service.ServiceCommand;
import com.smartbarber.domain.model.service.Service;
import com.smartbarber.domain.port.ServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateServiceUC {

    private final ServicePort servicePort;

    public Mono<Service> updateService(Integer id, ServiceCommand command) {
        return servicePort.findById(id)
                .map(existingService -> Service.update(
                        existingService.getId(),
                        command.barberiaId(),
                        command.name(),
                        command.description(),
                        command.duration(),
                        existingService.getStatus(),
                        command.offer(),
                        command.price(),
                        command.specialPrice(),
                        existingService.getCreateAt()
                ))
                .flatMap(updatedService ->
                        servicePort.update(id, updatedService)
                );
    }
}