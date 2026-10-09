package com.smartbarber.infrastructure.drivenadapter.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.smartbarber.domain.port.FirebaseAuthPort;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Component
public class FirebaseAuthAdapter implements FirebaseAuthPort {

    @Override
    public Mono<Boolean> isValidToken(String idToken) {
        return formatToken(idToken)
                .flatMap(token ->
                        getInstance(token).map(tokenFirebase ->
                                !tokenFirebase.isEmpty()).doOnNext(e -> System.out.println("TokenAAA: " + e)));
    }

    @Override
    public Mono<Boolean> isExpiredToken(String idToken) {
        return formatToken(idToken)
                .flatMap(token ->
                    getInstance(token).doOnNext(e -> System.out.println("ASDASDSADSA"+e))
                            .map(mapClaims -> {
                                System.out.println("idToken expired : " + mapClaims.get("exp"));
                                Instant now = Instant.now().truncatedTo(ChronoUnit.SECONDS);
                                long date = (Long) mapClaims.get("exp");
                                Instant expirationDate = Instant.ofEpochSecond(date);
                                System.out.println( "DATA VALIDATION + " + expirationDate.isBefore(now) + " NOW: " + Instant.now() + "expirationDate: " + expirationDate );
                                return expirationDate.isBefore(now);
                            })
                );

    }

    private Mono<String> formatToken(String idToken) {
        if (idToken == null || !idToken.startsWith("Bearer ")) {
            return Mono.error(() ->
                    new TechnicalExceptions(
                            TechnicalMessageExceptions.BAD_REQUEST
                    )
            );
        }
       return Mono.just(idToken.substring(7));

    }

    @Override
    public Mono<String> getUid(String idToken) {

        System.out.println("idToken user: " + idToken);
        return formatToken(idToken)
                .flatMap(token ->getInstance(token)
                        .map(mapClaims -> (String) mapClaims.get("user_id")).doOnNext(e -> System.out.println("getUid: " + e)));
    }


    private Mono<Map<String, Object>> getInstance(String idToken) {
        System.out.println("idToken: getInstance " + idToken);
            return Mono.fromCallable(() -> {
                FirebaseToken decode = FirebaseAuth.getInstance().verifyIdToken(idToken);
                        return decode.getClaims();
                    })
                    .doOnError(e -> e.printStackTrace())
                    .subscribeOn(Schedulers.boundedElastic());
    }
}
