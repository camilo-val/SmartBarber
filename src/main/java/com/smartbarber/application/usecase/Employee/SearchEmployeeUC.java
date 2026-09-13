package com.smartbarber.application.usecase.Employee;

import com.smartbarber.domain.port.EmployeePort;
import com.smartbarber.domain.exceptions.MessageExceptionsEmployee;
import com.smartbarber.domain.exceptions.EmployeeExceptions;
import com.smartbarber.domain.model.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class SearchEmployeeUC {
    private final EmployeePort employeePort;

    public Mono<Employee> buscarPorNombre(String nombre){
        return employeePort.buscarEmpleadoPorNombre(nombre)
                .doOnNext(employee -> log.info("Datos encontrados {}", employee))
                .switchIfEmpty(Mono.error(new EmployeeExceptions(MessageExceptionsEmployee.EMPLOYEE_NO_EXISTE)));
    }

    public Mono<Employee> buscarPorDocuemnto(String documento){
        return employeePort.buscarPorDocuemnto(documento)
                .doOnNext(employee -> log.info("Datos encontrados {}", employee))
                .switchIfEmpty(Mono.error(new EmployeeExceptions(MessageExceptionsEmployee.EMPLOYEE_NO_EXISTE)));
    }

    public Mono<Employee> buscarPorId(String id){
        return employeePort.buscarEmpleadoPorId(UUID.fromString(id))
                .doOnNext(employee -> log.info("Datos encontrados {}", employee))
                .switchIfEmpty(Mono.error(new EmployeeExceptions(MessageExceptionsEmployee.EMPLOYEE_NO_EXISTE)));
    }
}
