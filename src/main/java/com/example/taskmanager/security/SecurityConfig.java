package com.example.taskmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

public SecurityConfig(
        CustomUserDetailsService customUserDetailsService) {

    this.customUserDetailsService =
            customUserDetailsService;
}

@Bean
public AuthenticationManager authenticationManager(
        AuthenticationConfiguration config)
        throws Exception {

    return config.getAuthenticationManager();
}
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
             .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
  .userDetailsService(customUserDetailsService)       
            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/auth/**"
                ).permitAll()

                .requestMatchers(
                    "/admin/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    "/guest/**"
                ).hasRole("GUEST")

                .requestMatchers(
                    "/supervisor/**"
                ).hasRole("SUPERVISOR")

                .requestMatchers("/tasks/**").authenticated()

                .anyRequest()
                .authenticated() 
           );
          /*  .authorizeHttpRequests(auth ->
                auth.anyRequest().permitAll()
            );
 */
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
}
