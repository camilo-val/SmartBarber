package com.smartbarber.application.usecase.employee;

import com.smartbarber.application.command.in.employee.EmployeeCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.user.UserMessageExceptions;
import com.smartbarber.domain.port.EmployeePort;
import com.smartbarber.domain.exceptions.employee.MessageExceptionsEmployee;
import com.smartbarber.domain.model.employee.Employee;
import com.smartbarber.domain.port.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateEmployeeUC {

    private final EmployeePort employeePort;
    private final UserPort userPort;

    public Mono<Employee> createEmployee(EmployeeCommand employee){
        return Mono.when(
                validateUser(employee.user().firebaseId()),
                validateEmployee(employee.document())
        )
                .then(employeePort.existsByDocument(employee.document()))
                .flatMap(exist -> {
                    if (Boolean.TRUE.equals(exist)){
                        return Mono.error(() -> new BusinessExceptions(MessageExceptionsEmployee.EMPLOYEE_ALREADY_EXISTS));
                    }
                    return Mono.just(Employee.createEmployee(
                            employee.user().id(),
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

    private Mono<Void> validateUser(String uid){
        return userPort.existByFirebaseId(uid)
                .flatMap(existsUser -> {
                    if (existsUser) {
                        return Mono.error(() -> new BusinessExceptions(UserMessageExceptions.USER_ALREADY_EXISTS));
                    }
                    return Mono.empty();
                });
    }
    private Mono<Void> validateEmployee(String document){
        return employeePort.existsByDocument(document)
                .flatMap(existsEmployee -> {
                    if (existsEmployee) {
                        return Mono.error(() -> new BusinessExceptions(MessageExceptionsEmployee.EMPLOYEE_ALREADY_EXISTS));
                    }
                    return Mono.empty();
                });
    }
}
