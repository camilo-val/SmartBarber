
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
    public RouterFunction<ServerResponse> virtualQueueRoutes() {
        return RouterFunctions.route()
                .POST(
                        "/api/v1/queue/{employeeId}/open",
                        handler::openQueue
                )
                .POST(
                        "/api/v1/queue/{employeeId}/close",
                        handler::closeQueue
                )
                .POST(
                        "/api/v1/queue/{employeeId}/next",
                        handler::nextCustomer
                )
                .POST(
                        "/api/v1/queue/{employeeId}/complete",
                        handler::completeCurrentTurn
                )
                .POST(
                        "/api/v1/queue/join",
                        handler::joinQueue
                )
                .GET(
                        "/api/v1/queue/{employeeId}",
                        handler::getQueue
                )
                .GET(
                        "/api/v1/queue/{employeeId}/status",
                        handler::getQueueStatus
                )
                .build();
    }
}
