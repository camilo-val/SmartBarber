package com.smartbarber.infrastructure.entrypoint.reactiveweb.config;

import com.smartbarber.application.usecase.athentication.Authentication;
import com.smartbarber.domain.enums.RoleType;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class JwtFilter implements WebFilter {

    private final Authentication validateTokenUseCase;

    public JwtFilter(Authentication validateTokenUseCase) {
        this.validateTokenUseCase = validateTokenUseCase;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            WebFilterChain chain
    ) {
        String path = exchange.getRequest()
                .getPath()
                .value();

        // Las rutas públicas no necesitan JWT
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        String authorization = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // Sin token, Spring Security decidirá si permite el acceso
        if (authorization == null
                || !authorization.startsWith("Bearer ")
                || authorization.length() <= 7) {
            return chain.filter(exchange);
        }

        return validateTokenUseCase.authorization(authorization)

                // Solo los errores de validación se convierten en 401
                .onErrorResume(TechnicalExceptions.class, error -> {
                    log.error(error.getMessage(), error);
                    return Mono.error(() -> new TechnicalExceptions(TechnicalMessageExceptions.UNAUTHORIZED));
                })

                .flatMap(token -> {


                    // Lista de roles válidos para la aplicación
                    if (!List.of(
                            RoleType.CUSTOMER,
                            RoleType.BARBER,
                            RoleType.ADMIN
                    ).contains(token)) {
                        return Mono.error(() -> new TechnicalExceptions(TechnicalMessageExceptions.FORBIDDEN));
                    }

                    var authentication =
                            new UsernamePasswordAuthenticationToken(
                                    token,
                                    null,
                                    List.of(
                                            new SimpleGrantedAuthority(
                                                    "ROLE_" + token
                                            )
                                    )
                            );

                    return chain.filter(exchange)
                            .contextWrite(
                                    ReactiveSecurityContextHolder
                                            .withAuthentication(authentication)
                            );
                });
    }

    private boolean isPublicPath(String path) {
        return path.startsWith("/auth/")
                || path.equals("/auth")
                || path.startsWith("/reservation-service/")
                || path.equals("/reservation-service")
                || path.startsWith("/schedule-service/")
                || path.equals("/schedule-service")
                || path.startsWith("/public/")
                || path.equals("/public");
    }
}