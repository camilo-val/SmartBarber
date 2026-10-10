package com.smartbarber.application.usecase.barbershop;

import com.smartbarber.application.command.in.barbershop.BarberShopOnboardingCommand;
import com.smartbarber.application.mapper.BarbershopMapper;
import com.smartbarber.application.mapper.EmployeeMapper;
import com.smartbarber.application.mapper.UserMapper;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.barbershop.BarberShopMessageExceptions;
import com.smartbarber.domain.exceptions.employee.MessageExceptionsEmployee;
import com.smartbarber.domain.exceptions.user.UserMessageExceptions;
import com.smartbarber.domain.model.barbershop.BarberShopOnboarding;
import com.smartbarber.domain.model.barbershop.Barbershop;
import com.smartbarber.domain.model.employee.Employee;
import com.smartbarber.domain.model.user.User;
import com.smartbarber.domain.port.BarberShopRepositoryPort;
import com.smartbarber.domain.port.EmployeePort;
import com.smartbarber.domain.port.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class OnboardingBarberShopUc {
    private final BarberShopRepositoryPort barberShopRepositoryPort;
    private final UserPort userPort;
    private final EmployeePort employeePort;
    private final BarbershopMapper barbershopMapper;
    private final UserMapper userMapper;
    private final EmployeeMapper employeeMapper;
    private final TransactionalOperator transactionalOperator;

    public Mono<BarberShopOnboarding> execute(BarberShopOnboardingCommand command) {
        System.out.println("OnboardingBarberShopUc.execute: " + command);
        return Mono.when(validateBarber(command),
                            validateUser(command.user().firebaseId()),
                            validateEmployee(command.employee().document())
                        )
                .then(barberShopRepositoryPort.save(Barbershop.createBarbershop(
                            command.barbershop().name(),
                            command.barbershop().description(),
                            command.barbershop().location(),
                            command.barbershop().phone(),
                            command.barbershop().document(),
                            command.barbershop().documentType(),
                            command.barbershop().companyName()))
                        .flatMap(barbershop ->
                            userPort.save(User.createUserString(command.user().firebaseId(), command.user().roleId()))
                                    .flatMap(user -> employeePort.save(Employee.createEmployee(
                                            user.getId(),
                                            barbershop.getId(),
                                            command.employee().document(),
                                            command.employee().documentType(),
                                            command.employee().name(),
                                            command.employee().cell(),
                                            command.employee().email(),
                                            command.employee().specialty())
                                        ).map(employee -> BarberShopOnboarding.create(
                                            barbershopMapper.toCommand(barbershop),
                                            userMapper.toCommand(user),
                                            employeeMapper.toCommand(employee))
                                         ).doOnNext(barberShopOnboarding -> System.out.println("OnboardingBarberShopUc.execute: " + barberShopOnboarding))
                                    )
                        )
                ).as(transactionalOperator::transactional);
    }


    private Mono<Void> validateBarber(BarberShopOnboardingCommand command){
        return Mono.zip(barberShopRepositoryPort.existByCompanyName(command.barbershop().companyName()),
                        barberShopRepositoryPort.existByDocument(command.barbershop().document()),
                        barberShopRepositoryPort.existByName(command.barbershop().name()))
                .flatMap(tuple -> {
                    Boolean existByCompanyName = tuple.getT1();
                    Boolean existByDocument = tuple.getT2();
                    Boolean existByName = tuple.getT3();
                    if (existByCompanyName || existByDocument || existByName) {
                        return Mono.error(() -> new BusinessExceptions(BarberShopMessageExceptions.BARBERSHOP_ALREADY_EXIST));
                    }
                    return Mono.empty();
                });
    }

    private Mono<Void> validateUser(String uid){
        return userPort.existByFirebaseId(uid)
                .flatMap(existsUser -> {
                    if (existsUser) {
                        return Mono.error(() -> new BusinessExceptions(UserMessageExceptions.USUARIO_EXISTENTE));
                    }
                    return Mono.empty();
                });
    }
    private Mono<Void> validateEmployee(String document){
        return employeePort.existsByDocument(document)
                .flatMap(existsEmployee -> {
                    if (existsEmployee) {
                        return Mono.error(() -> new BusinessExceptions(MessageExceptionsEmployee.EMPLOYEE_EXISTENTE));
                    }
                    return Mono.empty();
                });
    }

}
