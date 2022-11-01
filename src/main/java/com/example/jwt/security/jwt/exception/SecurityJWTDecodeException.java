package com.example.jwt.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class SecurityJWTDecodeException extends AuthenticationException {
    public SecurityJWTDecodeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SecurityJWTDecodeException(String msg) {
        super(msg);
    }
}
