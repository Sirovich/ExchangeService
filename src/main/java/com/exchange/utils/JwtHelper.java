package com.exchange.utils;

import com.exchange.model.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
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

    public String getSubject(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public String getUserEmailFromRequest(HttpServletRequest request) {
        var token = extractJwtFromRequest(request);
        return getSubject(token);
    }
}