package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.port.ServicePort;
import com.smartbarber.domain.model.service.Service;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.ServiceData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.ServiceAdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class ServiceAdapter implements ServicePort{
    private final ServiceData serviceData;
    private final ServiceAdapterMapper mapper;
    @Override
    public Mono<Service> findById(Integer id){
        return serviceData.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Service> findByName(String name){
        return serviceData.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Service> save(Service service){
        return serviceData.save(mapper.toEntity(service))
                .doOnNext( e -> log.info("Data registrada {}", e.toString()))
                .doOnSuccess( entityGuardada ->
                        log.info("Proceso de guardado finalizado correctamente"))
                .doOnError( error ->
                        log.error("Error guardando barbería", error))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Service> update(Integer id, Service service){
        return serviceData.save(mapper.toEntity(service))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existByName(String name) {
        return serviceData.existsByName(name);
    }
}
