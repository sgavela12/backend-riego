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
                .requestMatchers("/alexa/**").permitAll() // Permitir acceso a /alexa/**
                .anyRequest().denyAll() 
            )
            .csrf(csrf -> csrf.disable()); 

        return http.build();
    }
}
