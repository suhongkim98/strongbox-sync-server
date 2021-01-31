package com.ssongk.accongbox.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssongk.accongbox.interceptor.AuthInterceptor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	private final AuthInterceptor authInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/login/**"); // 경로지정하기
	}
	
}
