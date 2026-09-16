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
        return RouterFunctions.route(RequestPredicates.POST(CLIENT_SERVICE + "/crear-cliente"),clientHandler::createClient)
                .andRoute(RequestPredicates.GET(CLIENT_SERVICE + "/name/{name}"),clientHandler::findClientByName)
                .andRoute(RequestPredicates.GET(CLIENT_SERVICE + "/document/{document}"),clientHandler::findByClientByDocument)
                .andRoute(RequestPredicates.GET(CLIENT_SERVICE + "/id/{id}"),clientHandler::findClientById)
                .andRoute(RequestPredicates.PUT(CLIENT_SERVICE + "/actualizar-cliente/{id}"),clientHandler::updateClient);
    }
}
