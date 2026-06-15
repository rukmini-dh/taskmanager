package com.example.taskmanager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.security.Key;

import java.util.Date;

@Component
public class JwtUtil {

    
    private final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey12345";

    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    // Generate signing key
    private Key getSigningKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes());
    }

    // Generate JWT token
    public String generateToken(
            String userName) {

        return Jwts.builder()

                .setSubject(userName)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION_TIME))

                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256)

                .compact();
    }

    // Extract username
    public String extractUserName(
            String token) {

        return extractClaims(token)
                .getSubject();
    }

    // Validate token
    public boolean validateToken(
            String token,
            String userName) {

        final String extractedUserName = extractUserName(token);

        return extractedUserName
                .equals(userName)
                &&
                !isTokenExpired(token);
    }

    // Check expiration
    private boolean isTokenExpired(
            String token) {

        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // Extract claims
private Claims extractClaims(String token) 
{

    return Jwts.parserBuilder()

        .setSigningKey(
            getSigningKey()
        )

        .build()

        .parseClaimsJws(token)

        .getBody();
}
}
