package com.example.demo.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;

@Service
public class JwtService {
    private static final String SECRET_SIGN_KEY = "V6SJKqsfA4mEYx0KC1LtMtzNrIwoCX7nKNXS3jNdQdRlrkA8kAE3XzyclkwD1AFJ";

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userdetails) {
        return generateToken(new HashMap<>(), userdetails);
    }

    public String generateToken(Map<String, Object> additionalClaims, UserDetails userdetails) {
        return Jwts.builder().claims(additionalClaims).subject(userdetails.getUsername()).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)).signWith(getSignInKey()).compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {

        return claimResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_SIGN_KEY);
        return new SecretKeySpec(keyByte, "HmacSHA256");
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUserEmail(token).equals(userDetails.getUsername()) && !isTokenValid(token);
    }

    public boolean isTokenValid(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
