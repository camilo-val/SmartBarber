package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.firebase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class FirebaseTestRuta {

    @Bean
    public RouterFunction<ServerResponse> firebaseTestRouter(
            FirebaseTestHandler handler
    ) {
        return route(
                POST("/firebase-test/verify"),
                handler::verifyToken
        );
    }
}