package com.chapter.foodcourt.insfrastructure.configuration.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public List<String> getRoles(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String authority = claims.get("authorities", String.class);
        return Collections.singletonList(authority);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            throw new JwtException("Invalid Jwt token");
        } catch (ExpiredJwtException e) {
            throw new JwtException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new JwtException("JWT claims string is empty");
        }
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("userId", Long.class);
    }
}