package com.example.jwt.security.provider;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwt.security.jwt.JWTDecoder;
import com.example.jwt.security.jwt.JWTVerifier;
import com.example.jwt.security.jwt.exception.JWTUnknownException;
import com.example.jwt.security.token.JWTPreProcessingToken;
import com.example.jwt.security.token.PostAuthorizationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {

    private JWTDecoder jwtDecoder;
    private JWTVerifier jwtVerifier;

    public JWTAuthenticationProvider(JWTDecoder jwtDecoder, JWTVerifier jwtVerifier) {
        this.jwtDecoder = jwtDecoder;
        this.jwtVerifier = jwtVerifier;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        DecodedJWT decodedJWT = jwtDecoder.decodeJwt(token).orElseThrow(() -> new JWTUnknownException("JWT 디코딩 중 알 수 없는 오류가 발생하였습니다."));

        jwtVerifier.verify(decodedJWT);

        return PostAuthorizationToken.getTokenFromDecodedJWT(decodedJWT);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTPreProcessingToken.class.isAssignableFrom(authentication);
    }
}