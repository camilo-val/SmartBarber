package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.client;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant.CLIENT_SERVICE;

@Configuration
@AllArgsConstructor
public class ClientRuta {

    private final ClientHandler clientHandler;

    @Bean
    public RouterFunction<ServerResponse> clientRutas(){
        return RouterFunctions.route(RequestPredicates.POST(CLIENT_SERVICE + "/crear-cliente"),clientHandler::crearCliente)
                .andRoute(RequestPredicates.GET(CLIENT_SERVICE + "/name/{name}"),clientHandler::buscarClientePorNombre)
                .andRoute(RequestPredicates.GET(CLIENT_SERVICE + "/document/{document}"),clientHandler::buscarClientePorDocuemnto)
                .andRoute(RequestPredicates.GET(CLIENT_SERVICE + "/id/{id}"),clientHandler::buscarClientePorId)
                .andRoute(RequestPredicates.PUT(CLIENT_SERVICE + "/actualizar-cliente/{id}"),clientHandler::actualizarCliente);
    }
}
