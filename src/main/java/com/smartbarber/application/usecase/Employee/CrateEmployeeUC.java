package com.smartbarber.application.usecase.employee;

import com.smartbarber.application.command.in.employee.EmployeeCommand;
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

    public Mono<Employee> crearEmpleado(EmployeeCommand employee){
        return employeePort.existsByDocument(employee.document())
                .flatMap(exist -> {
                    if (Boolean.TRUE.equals(exist)){
                        return Mono.error(() -> new BusinessExceptions(MessageExceptionsEmployee.EMPLOYEE_EXISTENTE));
                    }
                    return Mono.just(Employee.createEmployee(
                            employee.userId(),
                            employee.barberiaId(),
                            employee.document(),
                            employee.documentType(),
                            employee.name(),
                            employee.cell(),
                            employee.email(),
                            employee.specialty()
                    ));
                })
                .flatMap(employeePort::save);
    }
}
