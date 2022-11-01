package com.example.jwt.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class SecurityTokenExpiredException extends AuthenticationException {
    public SecurityTokenExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SecurityTokenExpiredException(String msg) {
        super(msg);
    }
}