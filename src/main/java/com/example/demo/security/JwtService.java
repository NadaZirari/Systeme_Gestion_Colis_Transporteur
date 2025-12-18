package com.example.demo.security;

import com.example.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.springframework.security.config.Elements.JWT;

@Service
public class JwtService {

    private static final String SECRET = "SECRET_KEY_123456";
    private static final String ISSUER = "LOGISTICS_API";
    private static final long EXPIRATION = 1000 * 60 * 60; // 1h

    public String generateToken(User user) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getLogin())
                .withClaim("role", user.getRole().name())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public String extractUsername(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }
}
