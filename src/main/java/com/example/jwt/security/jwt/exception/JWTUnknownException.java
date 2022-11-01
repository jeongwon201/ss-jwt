package com.example.jwt.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class JWTUnknownException extends AuthenticationException {
    public JWTUnknownException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JWTUnknownException(String msg) {
        super(msg);
    }
}
