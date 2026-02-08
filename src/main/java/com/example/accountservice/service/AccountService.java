package com.example.accountservice.service;

import com.example.accountservice.dto.MoneyTransferRequest;
import com.example.accountservice.model.Account;
import com.example.accountservice.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;

    public Account createAccount(Account account) {
        if (account.getIban() == null) {
            throw new RuntimeException("iban.not.null");
        }
        return repository.save(account);
    }

    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    @Transactional
    public void transferMoney(MoneyTransferRequest request) {
        Account fromAccount = repository.findByIban(request.getFromIban())
                .orElseThrow(() -> new RuntimeException("account.not.found"));

        Account toAccount = repository.findByIban(request.getToIban())
                .orElseThrow(() -> new RuntimeException("account.not.found"));

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("insufficient.balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        repository.save(fromAccount);
        repository.save(toAccount);
    }
}