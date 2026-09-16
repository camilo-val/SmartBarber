package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.barbershop;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant.BARBER_SERVICE;

@Configuration
@AllArgsConstructor
public class BarbershopRouter {

    private final BarbershopHandler barbershopHandler;

    @Bean
    public RouterFunction<ServerResponse> routerBarbershop(){
        return RouterFunctions.route(RequestPredicates.POST(BARBER_SERVICE + "/create-barber"), barbershopHandler::createBarbershop)
                .andRoute(RequestPredicates.GET(BARBER_SERVICE + "/name/{name}"), barbershopHandler::findBarbershopByNae)
                .andRoute(RequestPredicates.GET(BARBER_SERVICE + "/company-name/{companyName}"), barbershopHandler::findBarbershopByCompanyName)
                .andRoute(RequestPredicates.GET(BARBER_SERVICE + "/document/{document}"), barbershopHandler::findByBarbershopByDocument)
                .andRoute(RequestPredicates.GET(BARBER_SERVICE + "/id/{id}"), barbershopHandler::findBarbershopById)
                .andRoute(RequestPredicates.PUT(BARBER_SERVICE + "/update-barber/{id}"), barbershopHandler::updateBarbershop);
    }
}
