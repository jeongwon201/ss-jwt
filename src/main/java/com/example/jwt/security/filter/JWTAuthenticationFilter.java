package com.example.jwt.security.filter;

import com.example.jwt.security.jwt.HeaderTokenExtractor;
import com.example.jwt.security.token.JWTPreProcessingToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private HeaderTokenExtractor headerTokenExtractor;

    public JWTAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher, HeaderTokenExtractor headerTokenExtractor) {
        super(requiresAuthenticationRequestMatcher);
        this.headerTokenExtractor = headerTokenExtractor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String tokenPayload = request.getHeader("Authorization");
        JWTPreProcessingToken preAuthorizationToken = new JWTPreProcessingToken(headerTokenExtractor.extract(tokenPayload, request));

        return super.getAuthenticationManager().authenticate(preAuthorizationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getWriter().write((new ObjectMapper().writeValueAsString("JWT Authentication Error !")));
    }
}