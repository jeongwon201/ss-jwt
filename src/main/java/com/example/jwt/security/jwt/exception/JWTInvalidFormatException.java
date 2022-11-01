package com.example.jwt.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class JWTInvalidFormatException extends AuthenticationException {
    public JWTInvalidFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JWTInvalidFormatException(String msg) {
        super(msg);
    }
}
