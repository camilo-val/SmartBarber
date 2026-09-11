package com.smartbarber.application.usecase.Employee;

import com.smartbarber.application.port.EmployeePort;
import com.smartbarber.domain.exceptions.MessageExceptionsEmployee;
import com.smartbarber.domain.exceptions.EmployeeExceptions;
import com.smartbarber.domain.model.employee.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CrateEmployeeUC {

    private final EmployeePort employeePort;

    public Mono<Employee> crearEmpleado(Employee employee){
        return employeePort.existeEmpleadoPorNombre(employee.getNombre())
                .flatMap(exist -> {
                    if (Boolean.TRUE.equals(exist)){
                        Mono.error(new EmployeeExceptions(MessageExceptionsEmployee.EMPLOYEE_EXISTENTE));
                    }
                    return Mono.just(employee);
                })
                .flatMap(employeePort::crearEmpleado);
    }
}
