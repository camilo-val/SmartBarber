package com.smartbarber.infrastructure.entrypoint.reactiveweb.config;

import com.smartbarber.application.usecase.athentication.Authentication;
import com.smartbarber.domain.enums.RoleType;
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

@Component
public class JwtFilter implements WebFilter {

    private final Authentication validateTokenUseCase;

    public JwtFilter(Authentication validateTokenUseCase) {
        this.validateTokenUseCase = validateTokenUseCase;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            WebFilterChain chain) {

        String path = exchange.getRequest()
                .getPath()
                .value();

        /*if (path.startsWith("/auth/") || path.startsWith("/reservations") || path.startsWith("/schedule-service/")) {
            return chain.filter(exchange);
        }*/
        String authorization = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
        System.out.println("Authorization: " + authorization);
        if (authorization == null) {

            return chain.filter(exchange);
        }

        // Si no existe Authorization o no es Bearer,
        // dejamos que Spring Security continúe con el flujo.

        /*
         * Primero validamos el JWT.
         *
         * El onErrorResume está ANTES del flatMap para que solamente
         * los errores producidos al validar el JWT sean considerados
         * como 401.
         */
        return validateTokenUseCase.authorization(authorization)
                .flatMap(token -> {

                    System.out.println("ROLEEEEE: " + token.name());

                    /*
                     * El JWT es válido, pero el usuario no tiene
                     * el rol permitido.
                     */
                            ;
                    if (!"Cliente".equals(token.name()) && !"Barbero".equals(token.name()) && !"Administrador".equals(token.name())) {

                        exchange.getResponse()
                                .setStatusCode(HttpStatus.FORBIDDEN);

                        return exchange.getResponse().setComplete();
                    }

                    System.out.println("Valide exitoso");

                    /*
                     * Creamos la Authentication de Spring Security.
                     *
                     * IMPORTANTE:
                     * Esta NO es tu Authentication UC.
                     * Por eso utilizamos el nombre completo de la clase.
                     */
                    org.springframework.security.core.Authentication authentication =
                            new UsernamePasswordAuthenticationToken(
                                    token,
                                    null,
                                    List.of(
                                            new SimpleGrantedAuthority(
                                                    "ROLE_" + token.name()
                                            )
                                    )
                            );

                    /*
                     * Guardamos la autenticación en el contexto reactivo
                     * de Spring Security antes de continuar con la petición.
                     */
                    return chain.filter(exchange)
                            .contextWrite(
                                    ReactiveSecurityContextHolder
                                            .withAuthentication(authentication)
                            );
                });
    }
}