package com.smartbarber.application.usecase;

import com.smartbarber.application.port.BarberiaPort;
import com.smartbarber.domain.exceptions.BarberiaExcepciones;
import com.smartbarber.domain.exceptions.MensajesExcepcionBarberia;
import com.smartbarber.domain.model.barberia.Barberia;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CrearBarberiaUC {
    private final BarberiaPort barberiaPort;

    public Mono<Barberia> crearBarberia(Barberia barberia) {
        return barberiaPort.existeBarberiaPorNombre(barberia.getNombre())
                .flatMap(exist -> {
                    if(exist){
                        throw new BarberiaExcepciones(MensajesExcepcionBarberia.BARBERIA_EXISTENTE);
                    }
                    return Mono.just(barberia);
                }).map(newBarberia -> Barberia.crear(null,newBarberia.getNombre(),newBarberia.getDescripcion()
                        , newBarberia.getUbicacion(), newBarberia.getCelular(),newBarberia.getDocumento(),newBarberia.getTipoDocumento()
                ,newBarberia.getRazonSocial()))
                .flatMap(barberiaPort::crearBarberia);
    }
}
