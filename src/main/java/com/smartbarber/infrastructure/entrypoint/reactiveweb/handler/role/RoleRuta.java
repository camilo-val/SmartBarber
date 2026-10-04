package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.role;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant.ROLE_SERVICE;

@Configuration
@AllArgsConstructor
public class RoleRuta {

    private final RoleHandler roleHandler;

    @Bean
    public RouterFunction<ServerResponse> roterRole(){
        return RouterFunctions.route(RequestPredicates.POST(ROLE_SERVICE + "/create-role"),roleHandler::createRole)
                .andRoute(RequestPredicates.GET(ROLE_SERVICE + "/id/{id}"), roleHandler::findRoleById)
                .andRoute(RequestPredicates.PUT(ROLE_SERVICE + "/update-role/{id}"), roleHandler::updateRole);
    }
}
