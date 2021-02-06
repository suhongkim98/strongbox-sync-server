package com.ssongk.accongbox.core.security;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

public interface AuthTokenProvider<T> {
    T createAuthToken(String name, String role, String roomId, String vertificationCode, Date expiredDate);
    T convertAuthToken(String token);
    Optional<String> resolveToken(HttpServletRequest request);
}