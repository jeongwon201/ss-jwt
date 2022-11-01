package com.example.jwt.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwt.security.jwt.exception.SecurityJWTVerificationException;
import com.example.jwt.security.jwt.exception.SecurityTokenExpiredException;
import org.springframework.stereotype.Component;

@Component
public class JWTVerifier {

    public void verify(DecodedJWT decodedJWT) throws JWTVerificationException {
        try {
            Algorithm algorithm = Algorithm.HMAC256("test");
            com.auth0.jwt.interfaces.JWTVerifier verifier = JWT
                    .require(algorithm)
                    .withIssuer("init")
                    .build();

            verifier.verify(decodedJWT);
        } catch(Exception e) {
            if(e instanceof TokenExpiredException) {
                throw new SecurityTokenExpiredException(e.getMessage());
            } else {
                throw new SecurityJWTVerificationException(e.getMessage());
            }
        }
    }
}
