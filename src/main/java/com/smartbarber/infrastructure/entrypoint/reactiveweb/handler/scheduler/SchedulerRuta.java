package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant.SCHEDULE_SERVICE;

@Configuration
@AllArgsConstructor
public class SchedulerRuta {

    private final SchedulerHandler schedulerHandler;

    @Bean
    public RouterFunction<ServerResponse> schedulerRutas() {


        return RouterFunctions.route(RequestPredicates.POST(SCHEDULE_SERVICE + "/crear-schedule"),schedulerHandler::createSchedule)
                .andRoute(RequestPredicates.GET(SCHEDULE_SERVICE + "/id/{id}"),schedulerHandler::findScheduleById)
                .andRoute(RequestPredicates.GET(SCHEDULE_SERVICE + "/empleado/{empleadoId}"),schedulerHandler::findScheduleByEmpleadoId)
                .andRoute(RequestPredicates.PUT(SCHEDULE_SERVICE + "/actualizar-schedule/{id}"),schedulerHandler::updateSchedule);
    }
}
