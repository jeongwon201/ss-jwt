package com.example.jwt.account.application;

import com.example.jwt.account.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountService {
    private AccountRepository repository;
    private PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account save(AccountRequest request) {
        Account account = AccountBuilder.anAccount()
                .id(repository.generateId())
                .username(request.getEmail())
                .password(new Password(passwordEncoder.encode(request.getPassword().getValue())))
                .role(UserRole.ROLE_USER)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        return repository.save(account);
    }
}