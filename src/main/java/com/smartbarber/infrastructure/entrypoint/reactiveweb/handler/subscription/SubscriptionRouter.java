package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.subscription;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant.SUBSCRIPTION_SERVICE;

@Configuration
@AllArgsConstructor
public class SubscriptionRouter {
    private final HandlerSubscription handler;
    @Bean
    public RouterFunction<ServerResponse> routerSubscription(){
        return RouterFunctions.route(RequestPredicates.GET(SUBSCRIPTION_SERVICE + "/id/{id}"),handler::createSubscription)
                .andRoute(RequestPredicates.GET(SUBSCRIPTION_SERVICE + "/all"),handler::getAllSubscription)
                .andRoute(RequestPredicates.GET(SUBSCRIPTION_SERVICE + "/id/{id}"),handler::getSubscription);
    }
}
