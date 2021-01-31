package com.ssongk.accongbox.core.security;

import java.util.Date;

public interface AuthTokenProvider<T> {
    T createAuthToken(String id, String role, Date expiredDate);
    T convertAuthToken(String token);
}