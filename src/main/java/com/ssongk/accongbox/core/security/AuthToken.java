package com.ssongk.accongbox.core.security;

public interface AuthToken<T> {
    boolean validate();
    T getData();
}