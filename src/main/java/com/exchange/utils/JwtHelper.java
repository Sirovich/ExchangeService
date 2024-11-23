package com.exchange.utils;

import com.exchange.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Date;

@Component
public class JwtHelper {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
       return Jwts.builder()
               .setSubject(user.getEmail())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
               .signWith(SignatureAlgorithm.HS512, secret)
               .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }
}