package com.smartbarber.application.usecase.athentication;

import com.smartbarber.domain.enums.RoleType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.authorization.AuthorizationMessageExceptions;
import com.smartbarber.domain.model.Authentication;
import com.smartbarber.domain.model.role.Role;
import com.smartbarber.domain.port.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
@Log4j2
public class AuthenticationUC {
    private final FirebaseAuthPort firebaseAuthPort;
    private final UserPort userPort;
    private final BarberShopRepositoryPort barberShopRepositoryPort;
    private final EmployeePort employeePort;
    private final CustomerPort customerPort;
    private final RolePort rolePort;

    public Mono<RoleType> authorization(String idToken){
        System.out.println("Authentication.authorization: " + idToken);
        return firebaseAuthPort.isValidToken(idToken)
                .flatMap(isValid ->{
                    if (!isValid){
                        return Mono.error(()->new BusinessExceptions(AuthorizationMessageExceptions.THE_TOKEN_IS_EXPIRED));
                    }
                            return Mono.zip(firebaseAuthPort.isExpiredToken(idToken),firebaseAuthPort.getUid(idToken) )
                                    .map(tuple -> {
                                        Boolean isExpiredToken = tuple.getT1();
                                        String uidUser = tuple.getT2();
                                        if(!isExpiredToken){
                                            return uidUser;
                                        }
                                        throw new  BusinessExceptions(AuthorizationMessageExceptions.THE_TOKEN_IS_EXPIRED);
                                    }).flatMap(userPort::findByFirebaseId)
                                    .flatMap(user -> rolePort.findById(user.getRoleId()))
                                    .doOnNext(role -> log.info("role: {}" ,role))
                                    .map(Role::getRoleType)
                                    .doOnNext(roleType -> log.info("roleType: {}" ,roleType));
                        }
                    );
    }

    public Mono<Authentication> authorizationSession (String idToken){
        System.out.println("ENTREASASS");
        return authorization(idToken)
                .doOnNext(roleType -> log.info("roleTypeSSS: {}" ,roleType))
                .flatMap(roleType -> roleType != RoleType.CUSTOMER
                        ? getUserUid(idToken)
                          .doOnNext(uid -> log.info("uid: {}" ,uid))
                            .flatMap(uid ->
                                userPort.findByFirebaseId(uid)
                                    .flatMap(user -> employeePort.findByUserId(user.getId())
                                        .flatMap(employee -> barberShopRepositoryPort.findById(employee.getBarberiaId())
                                            .map(barberShop -> Authentication.mapEmployee(barberShop,employee,user))
                                        )))

                        : getUserUid(idToken)
                            .doOnNext(uid -> log.info("uidssss: {}" ,uid))
                                .flatMap(uid ->
                                    userPort.findByFirebaseId(uid)
                                    .doOnNext(user -> log.info("user: {}" ,user))
                                        .flatMap(user -> customerPort.findByUserId(user.getId())
                                                         .doOnNext(customer -> log.info("customer: {}" ,customer))
                                                .map(customer -> Authentication.mapCustomer(customer,user))
                                        ))
                            );
    }
    public Mono<String> getUserUid(String idToken){
        System.out.println("Authentication.getUserUid: " + idToken);
        return firebaseAuthPort.isValidToken(idToken).
                flatMap(mapClaims -> firebaseAuthPort.getUid(idToken));
    }
}
