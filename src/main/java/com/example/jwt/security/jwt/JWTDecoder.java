package com.example.jwt.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwt.security.jwt.exception.SecurityJWTDecodeException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JWTDecoder {
    public Optional<DecodedJWT> decodeJwt(String token) throws AuthenticationException {

        try {
            DecodedJWT decodedJWT = JWT.decode(token);

            return Optional.of(decodedJWT);
        } catch (Exception e) {
            if(e instanceof JWTDecodeException) {
                throw new SecurityJWTDecodeException(e.getMessage());
            }
        }

        return Optional.empty();
    }
}
