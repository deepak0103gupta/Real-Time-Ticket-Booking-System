package com.eventhub.auth_service.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.eventhub.auth_service.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final String secretKey = "mysecretekeyforrealtimeticletbookingapplication";
    
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    
    public String generateToken(User u){
        return Jwts.builder()
                    .subject(u.getEmail())
                    .claim("UserId", u.getId())
                    .claim("Name", u.getName())
                    .claim("Role", u.getRole())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 *60))
                    .signWith(getSignKey())
                    .compact();
    }

 public Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }
}
