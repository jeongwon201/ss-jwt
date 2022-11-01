package com.example.jwt.security.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwt.security.principal.PrincipalDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private PostAuthorizationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static PostAuthorizationToken getTokenFromUserDetails(PrincipalDetails principalDetails) {
        return new PostAuthorizationToken(principalDetails, "Encoded !", principalDetails.getAuthorities());
    }

    public static PostAuthorizationToken getTokenFromDecodedJWT(DecodedJWT decodedJWT) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(decodedJWT.getClaim("USER_ROLE").asString()));

        return new PostAuthorizationToken(decodedJWT.getSubject(), "Encoded !", grantedAuthorities);
    }

    public UserDetails getUserDetails() {
        return (UserDetails) super.getPrincipal();
    }
}