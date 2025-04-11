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
                // Permitir acceso a /alexa/** desde cualquier lugar
                .requestMatchers("/alexa/**").permitAll()
                
                // Permitir acceso solo si la IP es localhost o red local
                .requestMatchers(request -> {
                    String ip = request.getRemoteAddr();
                    return ip.equals("127.0.0.1") || ip.equals("::1") || ip.startsWith("192.168.1.");
                }).permitAll()
                
                // Denegar todo lo demás
                .anyRequest().denyAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
