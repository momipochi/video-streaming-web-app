package com.example.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private static final MacAlgorithm alg = Jwts.SIG.HS256;
    private static final SecretKey key = alg.key().build();
    private static JwtParser jwtParser;
    private static JwtBuilder jwtBuilder;

    public String extractUserEmail(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userdetails) {
        return generateToken(new HashMap<>(), userdetails);
    }

    public String generateToken(Map<String, Object> additionalClaims, UserDetails userdetails) {
        jwtBuilder = Jwts.builder();
        return jwtBuilder.subject(userdetails.getUsername()).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 86400000)).signWith(key)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        jwtParser = Jwts.parser().verifyWith(key).build();
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return (extractUserEmail(token).equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
