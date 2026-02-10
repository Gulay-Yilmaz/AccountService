package com.example.accountservice.service;

import com.example.accountservice.dto.CreateAccountRequest;
import com.example.accountservice.dto.MoneyTransferRequest;
import com.example.accountservice.model.Account;
import com.example.accountservice.model.enums.AccountStatus;
import com.example.accountservice.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final IbanGenerator ibanGenerator;

    public Account createAccount(CreateAccountRequest request) {

        if (repository.existsByCustomerIdAndAccountTypeAndCurrency(
                request.getCustomerId(),
                request.getAccountType(),
                request.getCurrency())) {
            throw new RuntimeException("account.already.exists");
        }

        Account account = Account.builder()
                .iban(generateUniqueIban())
                .customerId(request.getCustomerId())
                .accountHolderName(request.getAccountHolderName())
                .accountType(request.getAccountType())
                .currency(request.getCurrency())
                .balance(BigDecimal.ZERO)
                .status(AccountStatus.ACTIVE)
                .build();

        return repository.save(account);
    }

    private String generateUniqueIban() {
        String iban;
        do {
            iban = ibanGenerator.generate();
        } while (repository.existsByIban(iban));
        return iban;
    }

    @Cacheable(value = "accounts", key = "#iban")
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    @Transactional
    @CacheEvict(value = "accounts", allEntries = true)
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