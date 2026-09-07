package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.user;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant.USER_SERVICE;


@Configuration
@AllArgsConstructor
public class UserRuta {

    private final UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> userRutas(){
        return RouterFunctions.route(RequestPredicates.POST(USER_SERVICE + "/crear-usuario"),userHandler::createUser)
                .andRoute(RequestPredicates.GET(USER_SERVICE + "/id/{id}"),userHandler::SearchUserId)
                .andRoute(RequestPredicates.PUT(USER_SERVICE + "/actualizar-usuario/{id}"),userHandler::updateUser);
    }

}
