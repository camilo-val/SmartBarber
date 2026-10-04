package com.smartbarber.application.usecase.service;

import com.smartbarber.domain.port.ServicePort;
import com.smartbarber.domain.model.service.Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class UpdateServiceUC {
    private final ServicePort servicePort;

    public Mono<Service> serviceUpdate(String id, Service service){
        return servicePort.findById(Integer.valueOf(id))
                .map(serviceDB -> Service.update(serviceDB.getId(),service.getBarberiaId(),
                        service.getName(),service.getDescription(),service.getDuration(),serviceDB.getStatus(),
                        service.getOffert(),service.getPrice(),service.getSpecial_price(),serviceDB.getCreateAt()))
                .flatMap(serviceUpdate -> servicePort.update(Integer.valueOf(id), serviceUpdate));
    }
}
