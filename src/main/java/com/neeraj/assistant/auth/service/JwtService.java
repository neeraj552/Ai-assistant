package com.neeraj.assistant.auth.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSinginKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(String email){
        return Jwts.builder()
                 .setSubject(email)
                 .setIssuedAt(new Date())
                 .setExpiration(
                        new Date(System.currentTimeMillis() + expiration)
                 )
                 .signWith(
                         getSinginKey(),
                         SignatureAlgorithm.HS256
                 )
                 .compact();
    }
    public String extractEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String email){
        return extractEmail(token).equals(email) && !isTokeExpired(token);
    }

    private boolean isTokeExpired(String token){
        return extractAllClaims(token)
                 .getExpiration()
                 .before(new Date());
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
        .setSigningKey(getSinginKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

}
