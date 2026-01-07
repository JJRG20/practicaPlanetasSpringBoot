package com.example.sistemaplanetas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(
        HttpSecurity http,
        JwtAuthenticationConverter jwtAuthenticationConverter
    ) throws Exception {
        http
            // API REST → CSRF off
            .csrf(csrf -> csrf.disable())

            // Autorización
            .authorizeHttpRequests(auth -> auth
                // públicos
                .requestMatchers("/public/**").permitAll()

                // GET: admin y astro
                .requestMatchers(HttpMethod.GET, "/**")
                .hasAnyRole("admin", "astro")

                // POST, PUT, DELETE: solo admin
                .anyRequest().hasRole("admin")
            )

            // OAuth2 Resource Server (JWT)
            .oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt ->
                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)
                )
            );

        return http.build();
    }
}
