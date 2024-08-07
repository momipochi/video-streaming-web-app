package com.example.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private static final MacAlgorithm alg = Jwts.SIG.HS256;
    private static final SecretKey key = alg.key().build();

    public String extractUserEmail(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userdetails) {
        return generateToken(new HashMap<>(), userdetails);
    }

    public String generateToken(Map<String, Object> additionalClaims, UserDetails userdetails) {
        return Jwts.builder().claims(additionalClaims).subject(userdetails.getUsername()).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)).signWith(key, alg).compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {

        return claimResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).accept(Jws.CLAIMS).getPayload();
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
