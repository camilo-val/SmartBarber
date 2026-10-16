package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class VirtualQueueRouter {

    private final VirtualQueueHandler handler;

    @Bean
    public RouterFunction<ServerResponse> routes() {

        return RouterFunctions.route()

                .POST(
                        "/api/v1/queue/{barbershopId}/open",
                        handler::openQueue
                )

                .POST(
                        "/api/v1/queue/{barbershopId}/next",
                        handler::nextCustomer
                )

                .POST(
                        "/api/v1/queue/{barbershopId}/close",
                        handler::closeQueue
                )

                .build();
    }
}