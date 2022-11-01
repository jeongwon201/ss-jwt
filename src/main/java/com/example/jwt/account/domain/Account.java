package com.example.jwt.account.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity
public class Account {

    @EmbeddedId
    private AccountId id;
    private String username;

    @Embedded
    private Password password;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    protected Account() {}

    public Account(AccountId id, String username, Password password, UserRole role, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public AccountId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }
}