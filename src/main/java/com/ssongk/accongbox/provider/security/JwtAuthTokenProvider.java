package com.ssongk.accongbox.provider.security;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.ssongk.accongbox.core.security.AuthTokenProvider;

import io.jsonwebtoken.security.Keys;

public class JwtAuthTokenProvider implements AuthTokenProvider<JwtAuthToken> {
//토큰 생성, 변환
	private static final String AUTHORIZATION_HEADER = "x-auth-token";
    private final Key key;

    public JwtAuthTokenProvider(String secret) {
    	///토큰 발급에 사용하는 키를 지정한다
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public JwtAuthToken createAuthToken(String name, String role, String roomId, Date expiredDate) {
    	//토큰을 생성한다
        return new JwtAuthToken(name, role, roomId, expiredDate, key);
    }

    @Override
    public JwtAuthToken convertAuthToken(String token) {
    	//string토큰을 객체로 바꿔 반환한다.
        return new JwtAuthToken(token, key);
    }
    @Override
    public Optional<String> resolveToken(HttpServletRequest request) {
		//헤더에서 토큰 꺼내오기
        String authToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(authToken)) {
            return Optional.of(authToken);
        } else {
            return Optional.empty();
        }
    }
}