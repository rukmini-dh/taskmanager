package com.example.taskmanager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.taskmanager.security.CustomUserDetailsService;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


private final JwtUtil jwtUtil;
private final CustomUserDetailsService userDetailsService;

public JwtAuthenticationFilter(
    JwtUtil jwtUtil,
    CustomUserDetailsService userDetailsService
    ) {
    
   
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
    
    
    }
    

@Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
) throws ServletException, IOException {

    final String authHeader =
        request.getHeader("Authorization");

    String token = null;
    String username = null;

    // 1. Extract token
    if (authHeader != null &&
        authHeader.startsWith("Bearer ")) {

        token = authHeader.substring(7);
        username = jwtUtil.extractUserName(token);
    }

    // 2. If username exists and no authentication yet
    if (username != null) {

        UserDetails userDetails =
            userDetailsService.loadUserByUsername(username);

        // 3. Validate token
        boolean isValid =
jwtUtil.validateToken(token, username);

System.out.println(
"TOKEN VALID = " + isValid
);

if (isValid) {


UsernamePasswordAuthenticationToken authToken =
    new UsernamePasswordAuthenticationToken(
        userDetails,
        null,
        userDetails.getAuthorities()
    );

authToken.setDetails(
    new WebAuthenticationDetailsSource()
        .buildDetails(request)
);

SecurityContextHolder.getContext()
    .setAuthentication(authToken);

System.out.println(
    "AUTHENTICATION SET"
);

}

        /* if (jwtUtil.validateToken(token, username)) {
            System.out.println("TOKEN VALID");


            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );

            authToken.setDetails(
                new WebAuthenticationDetailsSource()
                    .buildDetails(request)
            );

            SecurityContextHolder.getContext()
                .setAuthentication(authToken);
        } */
    }
    System.out.println("AUTH HEADER: " + authHeader);

    System.out.println("TOKEN: " + token);
    
    System.out.println("USERNAME: " + username);
    
    filterChain.doFilter(request, response);
}


}
