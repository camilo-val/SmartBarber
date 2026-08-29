package com.smartbarber.application.usecase;

import com.smartbarber.application.port.BarberiaPort;
import com.smartbarber.domain.model.barberia.Barberia;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ActualizarBarberiaUC {
    private final BarberiaPort barberiaPort;

    public Mono<Barberia> actualizarBarberia(String id, Barberia barberia){

        return barberiaPort.buscarBarberiaPorId(UUID.fromString(id))
                .map(barberiaPort -> Barberia.actualizar(barberiaPort.getId(),barberia.getNombre(), barberia.getDescripcion(),
                        barberia.getUbicacion(), barberia.getCelular(), barberia.getDocumento(), barberia.getTipoDocumento(), barberia.getRazonSocial()
                ,barberia.getEstado(), barberiaPort.getFechaCreacion(), LocalDate.now()))
                .flatMap(barberiaModificada -> barberiaPort.actualizarBarberia(UUID.fromString(id), barberiaModificada));
    }

}

