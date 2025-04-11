package com.sergio.backend_riego.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permitir acceso a todas las rutas
            )
            .csrf(csrf -> csrf.disable()); // Deshabilitar CSRF para simplificar las pruebas

        return http.build();
    }
}
