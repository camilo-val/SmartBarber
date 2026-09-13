package com.smartbarber.application.usecase.Employee;

import com.smartbarber.domain.port.EmployeePort;
import com.smartbarber.domain.model.employee.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdateEmployeeUC {
    private final EmployeePort employeePort;

    public Mono<Employee> actualizarEmpleado(String id, Employee employee){

        return employeePort.buscarEmpleadoPorId(UUID.fromString(id))
                .map( employeeDB -> Employee.actualizar(employeeDB.getId(), employeeDB.getUserId(),
                        employeeDB.getBarberiaId(), employee.getDocumento(), employee.getTipoDocumento(),
                        employee.getNombre(), employee.getCelular(), employee.getCorreo(), employee.getEspecialidad(),
                        employeeDB.getFechaCreacion()))
                .flatMap( empleadoModificado -> employeePort.actualizarEmpleado(UUID.fromString(id), empleadoModificado));
    }
}
