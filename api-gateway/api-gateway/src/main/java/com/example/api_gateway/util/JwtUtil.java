package com.example.api_gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey123";

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Validate JWT Token
    public boolean validateToken(String token) {

        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // Extract Username
    public String extractUsername(String token) {

        return extractClaims(token).getSubject();
    }

    // Extract Role
    public String extractRole(String token) {

        return extractClaims(token).get("role", String.class);
    }

    // Extract Claims
    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}