package com.smartbarber.application.port;

import com.smartbarber.domain.model.client.Client;
import com.smartbarber.domain.model.employee.Employee;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EmployeePort {
    Mono<Employee> buscarEmpleadoPorId(UUID id);
    Mono<Employee> buscarEmpleadoPorNombre(String nombre);
    Mono<Employee> buscarPorDocuemnto(String documento);
    Mono<Employee> crearEmpleado(Employee employee);
    Mono<Employee> actualizarEmpleado( UUID id, Employee employee);
    Mono<Void> eliminarEmpleado(UUID id);
    Mono<Boolean> existeEmpleadoPorNombre(String nombreEmpleado);


}
