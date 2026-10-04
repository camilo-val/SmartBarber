package com.smartbarber.application.usecase.Employee;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.port.EmployeePort;
import com.smartbarber.domain.exceptions.employee.MessageExceptionsEmployee;
import com.smartbarber.domain.model.employee.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CrateEmployeeUC {

    private final EmployeePort employeePort;

    public Mono<Employee> crearEmpleado(Employee employee){
        return employeePort.existsByDocument(employee.getDocument())
                .flatMap(exist -> {
                    if (Boolean.TRUE.equals(exist)){
                        Mono.error(new BusinessExceptions(MessageExceptionsEmployee.EMPLOYEE_EXISTENTE));
                    }
                    return Mono.just(employee);
                })
                .flatMap(employeePort::save);
    }
}
