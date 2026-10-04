package com.smartbarber.application.usecase.athentication;

import com.smartbarber.domain.enums.RoleType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.authorization.AuthorizationMessageExceptions;
import com.smartbarber.domain.model.role.Role;
import com.smartbarber.domain.port.FirebaseAuthPort;
import com.smartbarber.domain.port.RolePort;
import com.smartbarber.domain.port.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Component
public class Authentication {
    private final FirebaseAuthPort firebaseAuthPort;
    private final UserPort userPort;
    private final RolePort rolePort;

    public Mono<RoleType> authorization(String idToken){
        System.out.println( "Empece: " + Instant.now().truncatedTo(ChronoUnit.SECONDS));
        return Mono.zip(firebaseAuthPort.isExpiredToken(idToken),firebaseAuthPort.verifyToken(idToken) )
                .map(tuple -> {
                    Boolean isExpiredToken = tuple.getT1();
                    String uidUser = tuple.getT2();
                    if(isExpiredToken){
                        System.out.println( "Dinalice: " + Instant.now().truncatedTo(ChronoUnit.SECONDS));

                        return uidUser;

                    }
                    throw new  BusinessExceptions(AuthorizationMessageExceptions.THE_TOKEN_IS_EXPIRED);
                }).flatMap(userPort::findByFirebaseId)
                .doOnNext( e ->         System.out.println( "Empece user: " + Instant.now().truncatedTo(ChronoUnit.SECONDS)))
                .flatMap(user -> rolePort.findById(user.getRoleId()))
                .doOnNext( e ->         System.out.println( "Empece role: " + Instant.now().truncatedTo(ChronoUnit.SECONDS)))
                .map(Role::getRoleType);

//        return firebaseAuthPort.isExpiredToken(idToken)
//                .flatMap(isValidToken ->{
//                    if (!isValidToken){
//                        return Mono.error(()->new BusinessExceptions(AuthorizationMessageExceptions.THE_TOKEN_IS_EXPIRED));
//                    }
//                    return firebaseAuthPort.verifyToken(idToken)
//                            .flatMap(userPort::findByFirebaseId)
//                            .flatMap(user -> rolePort.findById(user.getRoleId()))
//                            .map(Role::getRoleType);
//                });
    }
}
