package com.example.securitydemo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${variables.jwtSecret}")
    private String jwtSecret;

    @Value("${variables.jwtExpirationMS}")
    private int jwtExpirationMS;

    public String generateJWTUserDetails(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return generateJWT(userDetails.getUsername(), Collections.emptyMap(), jwtExpirationMS);
    }

    public String generateJWT(String sub, Map<String, Object> claims, int timeLive) {
        return Jwts.builder()
                .subject(sub)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + timeLive))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    /**
     * private SecretKey getSignInKey() {
     *     byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
     *     return Keys.hmacShaKeyFor(keyBytes);
     * }
     * private Claims extractAllClaims(String token) {
     *     return Jwts
     *             .parser()
     *             .verifyWith(getSignInKey())
     *             .build()
     *             .parseSignedClaims(token)
     *             .getPayload();
     * }
     */

    public Claims getBody(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid Signature");
        }
        return false;
    }
}