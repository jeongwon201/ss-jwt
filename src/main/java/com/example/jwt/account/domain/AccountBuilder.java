package com.example.jwt.account.domain;

import java.time.LocalDateTime;

public final class AccountBuilder {
    private AccountId id;
    private String username;
    private Password password;
    private UserRole role;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private AccountBuilder() {
    }

    public static AccountBuilder anAccount() {
        return new AccountBuilder();
    }

    public AccountBuilder id(AccountId id) {
        this.id = id;
        return this;
    }

    public AccountBuilder username(String username) {
        this.username = username;
        return this;
    }

    public AccountBuilder password(Password password) {
        this.password = password;
        return this;
    }

    public AccountBuilder role(UserRole role) {
        this.role = role;
        return this;
    }

    public AccountBuilder createDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public AccountBuilder updateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public Account build() {
        return new Account(id, username, password, role, createDate, updateDate);
    }
}
