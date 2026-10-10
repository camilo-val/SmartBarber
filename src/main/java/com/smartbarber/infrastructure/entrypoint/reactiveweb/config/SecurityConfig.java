package com.smartbarber.infrastructure.entrypoint.reactiveweb.config;

import com.smartbarber.domain.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter filter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http
    ) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .cors(cors ->
                        cors.configurationSource(corsConfigurationSource())
                )
                .authorizeExchange(exchange -> exchange
                        // Rutas públicas
                        .pathMatchers("/auth/**",
                                "/reservation-service/**",
                                "/schedule-service/**",
                                "/services-service/**",
                                "/reservation-review-service/**")
                                "/public/**")
                        .permitAll()
                        .pathMatchers(HttpMethod.GET, "/user-service/**")
                                .hasRole(String.valueOf(RoleType.Cliente))
                        //.permitAll()
                        .anyExchange()
                        .authenticated()
                        //.permitAll()
                ).addFilterAt(
                        filter,
                        SecurityWebFiltersOrder.AUTHENTICATION
                )
                /*.oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> {})
                )
                */
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}