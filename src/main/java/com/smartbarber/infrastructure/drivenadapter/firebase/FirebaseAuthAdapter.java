package com.smartbarber.infrastructure.drivenadapter.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.smartbarber.domain.port.FirebaseAuthPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Component
public class FirebaseAuthAdapter implements FirebaseAuthPort {

    @Override
    public Mono<String> verifyToken(String idToken) {
        return Mono.fromCallable(() -> {
            FirebaseToken decodedToken =
                    getInstance(idToken);
            return decodedToken.getUid();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Boolean> isExpiredToken(String idToken){
        return Mono.fromCallable(()->{FirebaseToken instanceToken = getInstance(idToken);
            Instant now = Instant.now().truncatedTo(ChronoUnit.SECONDS);
            Map<String, Object> claims = instanceToken.getClaims();
            long date = (Long) claims.get("exp");
            Instant expirationDate = Instant.ofEpochSecond(date);
            System.out.println(" now: " + now);
            System.out.println(" expiration: " + expirationDate);
            return !now.isAfter(expirationDate);}).subscribeOn(Schedulers.boundedElastic());

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
