package com.ssongk.accongbox.provider.security;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import com.ssongk.accongbox.core.security.AuthToken;
import com.ssongk.accongbox.exception.CustomJwtRuntimeException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Getter;

public class JwtAuthToken implements AuthToken<Claims> {
	//토큰 발급, 검증
    @Getter
    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    JwtAuthToken(String token, Key key) {
        this.token = token;
        this.key = key;
    }

    JwtAuthToken(String id, String role, Date expiredDate, Key key) {
        this.key = key;
        this.token = createJwtAuthToken(id, role, expiredDate).get();
    }

    @Override
    public boolean validate() {
        return getData() != null;
    }

    @Override
    public Claims getData() {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature.");
            throw new CustomJwtRuntimeException();
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token.");
            throw new CustomJwtRuntimeException();
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token.");
            throw new CustomJwtRuntimeException();
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token.");
            throw new CustomJwtRuntimeException();
        } catch (IllegalArgumentException e) {
            System.out.println("JWT token compact of handler are invalid.");
            throw new CustomJwtRuntimeException();
        }
    }

    private Optional<String> createJwtAuthToken(String id, String role, Date expiredDate) {

        var token = Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiredDate)
                .compact();

        return Optional.ofNullable(token);
    }
}