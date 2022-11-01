package com.example.jwt.security.provider;

import com.example.jwt.security.principal.PrincipalData;
import com.example.jwt.security.principal.PrincipalDataRepository;
import com.example.jwt.security.principal.PrincipalDetails;
import com.example.jwt.security.token.PostAuthorizationToken;
import com.example.jwt.security.token.PreAuthorizationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private PrincipalDataRepository repository;
    private PasswordEncoder passwordEncoder;

    public LoginAuthenticationProvider(PrincipalDataRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String username = token.getUsername();
        String password = token.getUserPassword();

        PrincipalData principalData = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디 또는 비밀번호를 잘못 입력했습니다."));

        if (!principalData.match(passwordEncoder, password))
            throw new BadCredentialsException("아이디 또는 비밀번호를 잘못 입력했습니다.");

        return PostAuthorizationToken.getTokenFromUserDetails(new PrincipalDetails(principalData));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean isCorrectPassword(String password, String accountPassword) {
        return passwordEncoder.matches(password, accountPassword);
    }
}