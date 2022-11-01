package com.example.jwt.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class JWTMismatchWithDatabaseException extends AuthenticationException {
    public JWTMismatchWithDatabaseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JWTMismatchWithDatabaseException(String msg) {
        super(msg);
    }
}
