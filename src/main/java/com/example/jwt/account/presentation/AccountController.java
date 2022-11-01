package com.example.jwt.account.presentation;

import com.example.jwt.account.application.AccountRequest;
import com.example.jwt.account.application.AccountService;
import com.example.jwt.account.domain.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/account")
    public ResponseEntity register(@Validated @RequestBody AccountDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body("valid error");

        Account account = service.save(new AccountRequest(dto));
        return ResponseEntity.ok(account);
    }

    @GetMapping("/test")
    public String test() {
        return "Success !";
    }

}