package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.domain.port.EmployeePort;
import com.smartbarber.domain.model.employee.Employee;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.EmployeeData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.EmployeeAdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class EmployeeAdapter  implements EmployeePort{
    private final EmployeeData employeeData;
    private final EmployeeAdapterMapper mapper;
    @Override
    public Mono<Employee> findById(UUID id){
        return employeeData.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Employee> findByName(String name){
        return employeeData.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Employee> findByDocument(String document){
        return employeeData.findByDocument(document)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Employee> save(Employee employee){
        return employeeData.save(mapper.toEntity(employee))
                .doOnNext(e -> log.info("Data registrada {}", e.toString()))
                .doOnSuccess( entityGuardada ->
                        log.info("Proceso de guardado finalizado correctamente"))
                .doOnError( error ->
                        log.error("Error guardando empleado", error))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Employee> update(UUID id, Employee employee){
        return employeeData.save(mapper.toEntity(employee))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> eliminarEmpleado(UUID id) { return employeeData.deleteById(id);}

    @Override
    public Mono<Boolean> existsByDocument(String document) { return employeeData.existsByDocument(document); }
}
