package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.barberia;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant.BARBERIA_SERVICE;

@Configuration
@AllArgsConstructor
public class BarberiaRuta {

    private final BarberiaHandler barberiaHandler;

    @Bean
    public RouterFunction<ServerResponse> barberiaRutas(){
        return RouterFunctions.route(RequestPredicates.POST(BARBERIA_SERVICE + "/crear-barberia"),barberiaHandler::crearBarberia)
                .andRoute(RequestPredicates.GET(BARBERIA_SERVICE + "/nombre/{nombre}"),barberiaHandler::buscarBarberiaPorNombre)
                .andRoute(RequestPredicates.GET(BARBERIA_SERVICE + "/razon-social/{razonSocial}"),barberiaHandler::buscarBarberiaPorRazonSocial)
                .andRoute(RequestPredicates.GET(BARBERIA_SERVICE + "/documento/{documento}"),barberiaHandler::buscarBarberiaPorDocumento)
                .andRoute(RequestPredicates.GET(BARBERIA_SERVICE + "/id/{id}"),barberiaHandler::buscarBarberiaPorId)
                .andRoute(RequestPredicates.PUT(BARBERIA_SERVICE + "/actualizar-barberia/{id}"),barberiaHandler::actualizarBarberia);
    }
}
