package com.ssongk.accongbox.provider.security;

import java.security.Key;
import java.util.Date;

import com.ssongk.accongbox.core.security.AuthTokenProvider;

import io.jsonwebtoken.security.Keys;

public class JwtAuthTokenProvider implements AuthTokenProvider<JwtAuthToken> {
//토큰 생성, 변환
    private final Key key;

    public JwtAuthTokenProvider(String secret) {
    	///토큰 발급에 사용하는 키를 지정한다
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public JwtAuthToken createAuthToken(String id, String role, Date expiredDate) {
    	//토큰을 생성한다
        return new JwtAuthToken(id, role, expiredDate, key);
    }

    @Override
    public JwtAuthToken convertAuthToken(String token) {
    	//string토큰을 객체로 바꿔 반환한다.
        return new JwtAuthToken(token, key);
    }
}