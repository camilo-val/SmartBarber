package com.smartbarber.domain.port;

import com.smartbarber.domain.model.client.Client;
import com.smartbarber.domain.model.employee.Employee;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EmployeePort {
    Mono<Employee> findById(UUID id);
    Mono<Employee> findByName(String name);
    Mono<Employee> findByDocument(String document);
    Mono<Employee> save(Employee employee);
    Mono<Employee> update( UUID id, Employee employee);
    Mono<Void> eliminarEmpleado(UUID id);
    Mono<Boolean> existsByDocument(String document);


}
