package com.example.jwt.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JWTPreProcessingToken extends UsernamePasswordAuthenticationToken {

    private JWTPreProcessingToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public JWTPreProcessingToken(String token) {
        this(token, token.length());
    }
}