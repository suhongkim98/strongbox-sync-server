package com.ssongk.accongbox.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.ssongk.accongbox.provider.security.JwtAuthTokenProvider;

@Configuration
public class JwtConfig {
	 @Value("${jwt.secret}")
	    private String secret;

	    @Bean
	    public JwtAuthTokenProvider jwtProvider() {
	        return new JwtAuthTokenProvider(secret);
	    }
}
