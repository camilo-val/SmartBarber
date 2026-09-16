package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.employee;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant.EMPLOYEE_SERVICE;

@Configuration
@AllArgsConstructor
public class EmployeeRuta {

    private final EmployeeHandler employeeHandler;

    @Bean
    public RouterFunction<ServerResponse> employeeRutas(){
        return RouterFunctions.route(RequestPredicates.POST(EMPLOYEE_SERVICE + "/crear-empleado"), employeeHandler::createEmployee)
                .andRoute(RequestPredicates.GET(EMPLOYEE_SERVICE + "/nombre/{nombre}"),employeeHandler::findEmployeeByName)
                .andRoute(RequestPredicates.GET(EMPLOYEE_SERVICE + "/documento/{documento}"),employeeHandler::findByEmployeeByDocument)
                .andRoute(RequestPredicates.GET(EMPLOYEE_SERVICE + "/id/{id}"),employeeHandler::findEmployeeById)
                .andRoute(RequestPredicates.PUT(EMPLOYEE_SERVICE + "/actualizar-empleado/{id}"),employeeHandler::updateEmployee);
    }
}
