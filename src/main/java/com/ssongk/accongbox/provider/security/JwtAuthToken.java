package com.ssongk.accongbox.provider.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
    private static final String ROOM_ID_KEY = "roomId";
    private static final String VERTIFICATION_CODE_KEY = "vertificationCode";

    JwtAuthToken(String token, Key key) {
        this.token = token;
        this.key = key;
    }

    JwtAuthToken(String id, String role, String roomId, String vertificationCode, Date expiredDate, Key key) {
        this.key = key;
        this.token = createJwtAuthToken(id, role, roomId, vertificationCode, expiredDate).get();
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

    private Optional<String> createJwtAuthToken(String name, String role, String roomId, String vertificationCode, Date expiredDate) {
    	
    	Map<String, Object> payloads = new HashMap<>(); // 값 넣기
    	payloads.put(AUTHORITIES_KEY, role);
    	payloads.put(ROOM_ID_KEY, roomId);
    	payloads.put(VERTIFICATION_CODE_KEY, vertificationCode);
        var token = Jwts.builder() //토큰발급
                .setSubject(name)
                .setClaims(payloads)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiredDate)
                .compact();

        return Optional.ofNullable(token);
    }
}