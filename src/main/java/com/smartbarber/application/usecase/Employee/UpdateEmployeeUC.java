package com.smartbarber.application.usecase.Employee;

import com.smartbarber.application.port.EmployeePort;
import com.smartbarber.domain.model.employee.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdateEmployeeUC {
    private final EmployeePort employeePort;

    public Mono<Employee> employeeUpdate(String id, Employee employee){

        return employeePort.findById(UUID.fromString(id))
                .map( employeeDB -> Employee.update(employeeDB.getId(), employeeDB.getUserId(),
                        employeeDB.getBarberiaId(), employee.getDocument(), employee.getDocumentType(),
                        employee.getName(), employee.getCell(), employee.getEmail(), employee.getSpecialty(),
                        employeeDB.getCreateAt()))
                .flatMap( employeeUpdate -> employeePort.update(UUID.fromString(id), employeeUpdate));
    }
}
