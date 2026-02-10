package com.example.accountservice.service;

import com.example.accountservice.dto.CreateAccountRequest;
import com.example.accountservice.model.Account;
import com.example.accountservice.model.enums.AccountStatus;
import com.example.accountservice.model.enums.AccountType;
import com.example.accountservice.model.enums.CurrencyType;
import com.example.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private IbanGenerator ibanGenerator;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldCreateAccountSuccessfully() {
        // given
        CreateAccountRequest request = new CreateAccountRequest();
        request.setCustomerId("CUST-123");
        request.setAccountHolderName("Gülay Yılmaz");
        request.setAccountType(AccountType.CHECKING);
        request.setCurrency(CurrencyType.TRY);

        when(accountRepository.existsByCustomerIdAndAccountTypeAndCurrency(
                request.getCustomerId(),
                request.getAccountType(),
                request.getCurrency()
        )).thenReturn(false);

        when(ibanGenerator.generate()).thenReturn("TR000000000000000000000001");
        when(accountRepository.existsByIban(anyString())).thenReturn(false);

        when(accountRepository.save(any(Account.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Account account = accountService.createAccount(request);

        // then
        assertThat(account).isNotNull();
        assertThat(account.getIban()).isEqualTo("TR000000000000000000000001");
        assertThat(account.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(account.getStatus()).isEqualTo(AccountStatus.ACTIVE);
        assertThat(account.getAccountType()).isEqualTo(AccountType.CHECKING);
        assertThat(account.getCurrency()).isEqualTo(CurrencyType.TRY);

        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionIfAccountAlreadyExists() {
        // given
        CreateAccountRequest request = new CreateAccountRequest();
        request.setCustomerId("CUST-123");
        request.setAccountType(AccountType.CHECKING);
        request.setCurrency(CurrencyType.TRY);

        when(accountRepository.existsByCustomerIdAndAccountTypeAndCurrency(
                anyString(), any(), any()
        )).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> accountService.createAccount(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("account.already.exists");

        verify(accountRepository, never()).save(any());
    }

}
