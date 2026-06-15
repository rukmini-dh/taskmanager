package com.example.taskmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import java.util.List;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig( JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
    
        this.jwtAuthenticationFilter =  jwtAuthenticationFilter;
       
    }
    @Configuration
    public class AppConfig {
    
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }  

@Bean
public AuthenticationManager authenticationManager(
        AuthenticationConfiguration config)
        throws Exception {

    return config.getAuthenticationManager();
}
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

       
        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )
            .authorizeHttpRequests(auth -> auth
        
                .requestMatchers("/auth/**")
                .permitAll()
                .requestMatchers("/ai/**").permitAll()   
                .anyRequest()
                .permitAll()
            )
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );
        
        return http.build();
      
        
        }
        
    @Bean
public CorsConfigurationSource
corsConfigurationSource() {


CorsConfiguration configuration =
    new CorsConfiguration();

configuration.setAllowedOrigins(
    List.of("http://localhost:3000")
);

configuration.setAllowedMethods(
    List.of("*")
);

configuration.setAllowedHeaders(
    List.of("*")
);

configuration.setAllowCredentials(true);

UrlBasedCorsConfigurationSource source =
    new UrlBasedCorsConfigurationSource();

source.registerCorsConfiguration(
    "/**",
    configuration
);

return source;


}

    @Bean
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
}
