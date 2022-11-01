package com.example.jwt.security.principal;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private PrincipalData principalData;

    public PrincipalDetails(PrincipalData principalData) {
        this.principalData = principalData;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new SimpleGrantedAuthority(principalData.getRole()));

        return collect;
    }

    @Override
    public String getPassword() {
        return "Encoded !";
    }

    @Override
    public String getUsername() {
        return principalData.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public PrincipalData getPrincipalData() {
        return principalData;
    }
}
