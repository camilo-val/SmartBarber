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
    private static Map<String, Object> claims = new HashMap<>();
    @Override
    public Mono<Boolean> isValidToken(String idToken) {
        System.out.println("FirebaseAuthAdapter.isValidToken: " + idToken);
        if (idToken == null || !idToken.startsWith("Bearer ")) {
            return Mono.error(() ->
                    new TechnicalExceptions(
                            TechnicalMessageExceptions.BAD_REQUEST
                    )
            );
        }
        String token = idToken.substring(7);
        return Mono.fromCallable(() -> {
            FirebaseToken decodedToken =
                    getInstance(token);
            this.claims = decodedToken.getClaims();
            return !decodedToken.getClaims().isEmpty();
        }).thenReturn(true).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Boolean> isExpiredToken(String idToken){
        return Mono.just(this.claims)
                .map(mapClaims ->{
                    Instant now = Instant.now().truncatedTo(ChronoUnit.SECONDS);
                    long date = (Long) mapClaims.get("exp");
                    Instant expirationDate = Instant.ofEpochSecond(date);
                    return expirationDate.isBefore(now);
                });

    }

    @Override
    public Mono<String> getUid(String idToken) {
        return Mono.just(this.claims)
                .map(mapClaims -> (String) mapClaims.get("user_id")).doOnNext(e -> System.out.println("getUid: " + e));
    }


    private static FirebaseToken getInstance(String idToken){
        try {
            return FirebaseAuth.getInstance().verifyIdToken(idToken);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
