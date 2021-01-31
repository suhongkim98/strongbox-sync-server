package com.ssongk.accongbox.interceptor;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssongk.accongbox.exception.CustomJwtRuntimeException;
import com.ssongk.accongbox.provider.security.JwtAuthToken;
import com.ssongk.accongbox.provider.security.JwtAuthTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {
	private final JwtAuthTokenProvider jwtAuthTokenProvider;
	private static final String AUTHORIZATION_HEADER = "x-auth-token";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Optional<String> token = resolveToken(request);
		if(token.isPresent()) {
			//토큰이 존재한다면 해당 string을 객체로 변환해서 검증한다
			JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
			if(jwtAuthToken.validate()) {
                return true;
            }
			throw new CustomJwtRuntimeException();
		}
		throw new CustomJwtRuntimeException();
	}
	
	private Optional<String> resolveToken(HttpServletRequest request) {
		//헤더에서 토큰 꺼내오기
        String authToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(authToken)) {
            return Optional.of(authToken);
        } else {
            return Optional.empty();
        }
    }
}
