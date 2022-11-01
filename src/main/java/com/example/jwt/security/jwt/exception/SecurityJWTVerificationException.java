package com.example.jwt.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class SecurityJWTVerificationException extends AuthenticationException {
    public SecurityJWTVerificationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SecurityJWTVerificationException(String msg) {
        super(msg);
    }
}