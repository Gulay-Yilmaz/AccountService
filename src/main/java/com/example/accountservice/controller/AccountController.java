package com.example.accountservice.controller;

import com.example.accountservice.dto.MoneyTransferRequest;
import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody Account account) {
        return ResponseEntity.ok(service.createAccount(account));
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        return ResponseEntity.ok(service.getAllAccounts());
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody MoneyTransferRequest request) {
        service.transferMoney(request);
        return ResponseEntity.ok("transfer.success");
    }
}
