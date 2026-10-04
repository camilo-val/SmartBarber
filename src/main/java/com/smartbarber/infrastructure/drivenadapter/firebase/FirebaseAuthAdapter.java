package com.smartbarber.infrastructure.drivenadapter.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.smartbarber.domain.port.FirebaseAuthPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class FirebaseAuthAdapter implements FirebaseAuthPort {

    @Override
    public Mono<String> verifyToken(String idToken) {
        return Mono.fromCallable(() -> {
            FirebaseToken decodedToken =
                    FirebaseAuth.getInstance().verifyIdToken(idToken);

            return decodedToken.getUid();
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
