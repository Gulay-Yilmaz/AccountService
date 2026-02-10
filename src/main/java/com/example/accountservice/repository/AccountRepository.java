package com.example.accountservice.repository;

import com.example.accountservice.model.Account;
import com.example.accountservice.model.enums.AccountType;
import com.example.accountservice.model.enums.CurrencyType;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByIban(String iban);

    boolean existsByIban(String iban);

    boolean existsByCustomerIdAndAccountTypeAndCurrency(
            String customerId,
            AccountType accountType,
            CurrencyType currency
    );
}
