package com.example.find_people.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final FirebaseTokenFilter firebaseTokenFilter;

    public SecurityConfig(FirebaseTokenFilter firebaseTokenFilter) {
        this.firebaseTokenFilter = firebaseTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/user/**", "/error/**", "/swagger-ui/**", "/v3/api-docs/**", "/api/board", "/api/board/list", "/api/board/main", "/api/admin/set-role/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        firebaseTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
