package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.application.port.BarberiaPort;
import com.smartbarber.domain.model.barberia.Barberia;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.BarberiaData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.BarberiaAdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class BarberiaAdapter implements BarberiaPort {
    private final BarberiaData barberiaData;
    private final BarberiaAdapterMapper mapper;
    @Override
    public Mono<Barberia> buscarBarberiaPorId(UUID id) {
        return barberiaData.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Barberia> buscarBarberiaPorNombre(String nombre) {
        return barberiaData.findByNombre(nombre)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Barberia> buscarBarberiaPorRazonSocial(String razonSocial) {
        return barberiaData.findByRazonSocial(razonSocial)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Barberia> buscarPorDocumento(String document) {
        return barberiaData.findByDocumento(document)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Barberia> crearBarberia(Barberia barberia) {
        return barberiaData.save(mapper.toEntity(barberia))
                .doOnNext(e -> log.info("Data registrada {}", e.toString()))
                .doOnSuccess(entityGuardada ->
                        log.info("Proceso de guardado finalizado correctamente"))
                .doOnError(error ->
                        log.error("Error guardando barbería", error))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Barberia> actualizarBarberia(UUID id, Barberia barberia) {
        return barberiaData.save(mapper.toEntity(barberia))
                .map(mapper::toDomain);    }

    @Override
    public Mono<Void> eliminarBarberia(UUID id) {
        return barberiaData.deleteById(id);
    }

    @Override
    public Mono<Boolean> existeBarberiaPorNombre(String nombreBarberia) {
        return barberiaData.findByNombre(nombreBarberia)
                .map(mapper::toDomain).hasElement();
    }
}
