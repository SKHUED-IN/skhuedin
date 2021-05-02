package com.skhuedin.skhuedin.infra;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {

    private Key secretKey;
    private long validityInMilliseconds;

    public JwtTokenProvider() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.validityInMilliseconds = 3600000;
    }

    //토큰생성
    public String createToken(String subject) {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");

        Date now = new Date();

        Date validity = new Date(now.getTime()
                + validityInMilliseconds);

        Claims claims = Jwts.claims().setSubject(subject).setIssuer("skhuedin").setExpiration(validity).setIssuedAt(now);

        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    //토큰에서 값 추출
    public String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    //유효한 토큰인지 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}