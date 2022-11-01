package com.example.jwt.security.jwt;

import com.example.jwt.security.jwt.exception.JWTInvalidFormatException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class HeaderTokenExtractor {

    public final String HEADER_PREFIX = "Bearer ";

    public String extract(String header, HttpServletRequest request) {
        if (header == null || header.equals("") || header.length() < HEADER_PREFIX.length()) {
            throw new JWTInvalidFormatException("올바른 JWT 정보가 아닙니다.");
        }

        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}