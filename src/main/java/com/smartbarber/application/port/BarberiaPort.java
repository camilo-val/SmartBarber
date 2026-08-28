package com.smartbarber.application.port;

import com.smartbarber.domain.model.barberia.Barberia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BarberiaPort {
    Mono<Barberia> buscarBarberiaPorId(UUID id);
    Mono<Barberia> buscarBarberiaPorNombre(String nombre);
    Flux<Barberia> buscarBarberiaPorRazonSocial(String razonSocial);
    Mono<Barberia> buscarPorDocumento(String document);
    Mono<Barberia> crearBarberia(Barberia barberia);
    Mono<Barberia> actualizarBarberia(Barberia barberia);
    Mono<Void> eliminarBarberia(UUID id);
    Mono<Boolean> existeBarberiaPorNombre(String nombreBarberia);
}
