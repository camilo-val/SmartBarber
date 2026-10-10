package com.smartbarber.application.usecase.customer;

import com.smartbarber.application.command.in.customer.CustomerCommand;
import com.smartbarber.application.command.in.customer.CustomerOnboardingCommand;
import com.smartbarber.application.mapper.CustomerMapper;
import com.smartbarber.application.mapper.UserMapper;
import com.smartbarber.domain.enums.RoleType;
import com.smartbarber.domain.exceptions.customer.CustomerMessageExceptions;
import com.smartbarber.domain.model.customer.Customer;
import com.smartbarber.domain.model.customer.CustomerOnboarding;
import com.smartbarber.domain.model.user.User;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.port.CustomerPort;
import com.smartbarber.domain.port.UserPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;


@Component
@AllArgsConstructor
@Slf4j
public class CreateCustomerUC {
    private final CustomerPort customerPort;
    private final UserPort userPort;
    private final UserMapper userMapper;
    private final CustomerMapper  customerMapper;
    private final TransactionalOperator transactionalOperator;

    public Mono<Customer> createCustomer(CustomerCommand customer){
        return customerPort.existsByDocument(customer.document())
                .flatMap( exist-> {
                    if (Boolean.TRUE.equals(exist)){
                        return Mono.error(() -> new BusinessExceptions(CustomerMessageExceptions.CUSTOMER_ALREADY_EXISTS));
                    }
                    return Mono.just(Customer.createCustomer(customer.userId(),
                            customer.document(),
                            customer.documentType(),
                            customer.name(),
                            customer.cell(),
                            customer.email()));
                })
                .flatMap(customerPort::save);
    }

    public Mono<CustomerOnboarding> onBoardingClient(CustomerOnboardingCommand customerOnboarding){
        System.out.println("CreateCustomerUC.onBoardingClient customerOnboarding: " + customerOnboarding);
        System.out.println("Role + " +(short) RoleType.CUSTOMER.getId());
        return Mono.when(validateCustomer(customerOnboarding.customer().document()),
                        validateUser(customerOnboarding.user().firebaseId()))
                .then(userPort.save(User.createUserString(customerOnboarding.user().firebaseId(),
                                (short) RoleType.CUSTOMER.getId()))
                        .doOnNext(user -> log.info("CreateCustomerUC.createCustomer user: {} userId: {}", user, user.getId()))
                        .flatMap(user -> customerPort.save(Customer.createCustomer(user.getId(),
                                customerOnboarding.customer().document(),
                                customerOnboarding.customer().documentType(),
                                customerOnboarding.customer().name(),
                                customerOnboarding.customer().cell(),
                                customerOnboarding.customer().email())
                                ).map(newCustomer -> CustomerOnboarding.create(
                                        customerMapper.toCustomerCommand(newCustomer)
                                        ,userMapper.toCommand(user)
                                )))
                )
                .as(transactionalOperator::transactional);
    }

    private Mono<Void> validateCustomer(String document){
        return customerPort.existsByDocument(document)
                .doOnNext(exists -> log.info("CreateCustomerUC.validateCustomer document: {}, exists: {}", document, exists))
                .flatMap(exists -> Boolean.TRUE.equals(exists)
                        ? Mono.error(() -> new BusinessExceptions(CustomerMessageExceptions.CUSTOMER_ALREADY_EXISTS))
                        : Mono.empty());
    }

    private Mono<Void> validateUser(String uid){
        return userPort.existByFirebaseId(uid)
                .doOnNext(exists -> log.info("CreateCustomerUC.validateUser uid: {}, exists: {}", uid, exists))
                .flatMap(exists -> Boolean.TRUE.equals(exists)
                        ? Mono.error(() -> new BusinessExceptions(CustomerMessageExceptions.CUSTOMER_ALREADY_EXISTS))
                        : Mono.empty());
    }
}
