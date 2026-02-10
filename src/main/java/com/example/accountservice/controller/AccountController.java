package com.example.accountservice.controller;

import com.example.accountservice.dto.CreateAccountRequest;
import com.example.accountservice.dto.MoneyTransferRequest;
import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;
    private final MessageSource messageSource;

    @PostMapping
    public ResponseEntity<Account> create(@Valid @RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(service.createAccount(request));
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        return ResponseEntity.ok(service.getAllAccounts());
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@Valid @RequestBody MoneyTransferRequest request) {
        service.transferMoney(request);
        return ResponseEntity.ok(messageSource.getMessage("transfer.success", null, LocaleContextHolder.getLocale()));
    }
}
