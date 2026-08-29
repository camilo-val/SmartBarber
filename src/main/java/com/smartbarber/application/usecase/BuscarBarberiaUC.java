package com.smartbarber.application.usecase;

import com.smartbarber.application.port.BarberiaPort;
import com.smartbarber.domain.exceptions.BarberiaExcepciones;
import com.smartbarber.domain.exceptions.MensajesExcepcionBarberia;
import com.smartbarber.domain.model.barberia.Barberia;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class BuscarBarberiaUC {
    private final BarberiaPort barberiaPort;

    public Mono<Barberia> buscarPorNombre(String nombre) {
        return barberiaPort.buscarBarberiaPorNombre(nombre)
                .doOnNext(barberia -> log.info("Datos encontrados {}",  barberia))
                .switchIfEmpty(Mono.error(new BarberiaExcepciones(MensajesExcepcionBarberia.BARBERIA_NO_EXISTE)));
    }

    public Flux<Barberia> buscarPorRazonSocial(String razonSocial) {
        return barberiaPort.buscarBarberiaPorRazonSocial(razonSocial)
                .doOnNext(barberia -> log.info("Datos encontrados {}",  barberia))
                .switchIfEmpty(Mono.error(new BarberiaExcepciones(MensajesExcepcionBarberia.BARBERIA_NO_EXISTE)));
    }

    public Mono<Barberia> bucarPorDocumento(String documento) {
        return barberiaPort.buscarPorDocumento(documento)
                .doOnNext(barberia -> log.info("Datos encontrados {}",  barberia))
                .switchIfEmpty(Mono.error(new BarberiaExcepciones(MensajesExcepcionBarberia.BARBERIA_NO_EXISTE)));
    }

    public Mono<Barberia> buscarPorId(String id) {
        return barberiaPort.buscarBarberiaPorId(UUID.fromString(id))
                .doOnNext(barberia -> log.info("Datos encontrados {}",  barberia))
                .switchIfEmpty(Mono.error(new BarberiaExcepciones(MensajesExcepcionBarberia.BARBERIA_NO_EXISTE)));
    }

}
