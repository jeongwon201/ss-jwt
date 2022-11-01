package com.example.jwt.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.security.principal.PrincipalDetails;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class JWTFactory {

    public String issueAccessToken(PrincipalDetails principalDetails) {

        try {
            return JWT.create()
                    .withIssuer("init")
                    .withSubject(principalDetails.getPrincipalData().getId())
                    .withClaim("USER_ROLE", principalDetails.getPrincipalData().getRole())
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000000))
                    .sign(generateAlgorithm());
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Algorithm generateAlgorithm() throws UnsupportedEncodingException {
        String signingKey = "test";
        return Algorithm.HMAC256(signingKey);
    }
}
