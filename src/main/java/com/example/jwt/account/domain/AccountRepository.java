package com.example.jwt.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public interface AccountRepository extends JpaRepository<Account, AccountId> {
    default AccountId generateId() {
        int randomNo = ThreadLocalRandom.current().nextInt(900000) + 100000;
        String number = String.format("%tY%<tm%<td%<tH%d", new Date(), randomNo);
        return new AccountId(number);
    }
}