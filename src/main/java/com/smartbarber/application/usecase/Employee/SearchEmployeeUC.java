package com.smartbarber.application.usecase.Employee;

import com.smartbarber.application.port.EmployeePort;
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

    public Mono<Employee> buscarPorNombre(String name){
        return employeePort.findByName(name)
                .doOnNext(employee -> log.info("Datos encontrados {}", employee))
                .switchIfEmpty(Mono.error(new EmployeeExceptions(MessageExceptionsEmployee.EMPLOYEE_NO_EXISTE)));
    }

    public Mono<Employee> buscarPorDocuemnto(String document){
        return employeePort.findByDocument(document)
                .doOnNext(employee -> log.info("Datos encontrados {}", employee))
                .switchIfEmpty(Mono.error(new EmployeeExceptions(MessageExceptionsEmployee.EMPLOYEE_NO_EXISTE)));
    }

    public Mono<Employee> buscarPorId(String id){
        return employeePort.findById(UUID.fromString(id))
                .doOnNext(employee -> log.info("Datos encontrados {}", employee))
                .switchIfEmpty(Mono.error(new EmployeeExceptions(MessageExceptionsEmployee.EMPLOYEE_NO_EXISTE)));
    }
}
