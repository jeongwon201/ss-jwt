package com.example.jwt.account.application;

import com.example.jwt.account.domain.Password;
import com.example.jwt.account.presentation.AccountDto;

public class AccountRequest {
    private String email;
    private Password password;

    public AccountRequest(String email, Password password) {
        this.email = email;
        this.password = password;
    }

    public AccountRequest(AccountDto dto) {
        this.email = dto.getUsername();
        this.password = new Password(dto.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }
}