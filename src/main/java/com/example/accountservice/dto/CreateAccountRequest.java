package com.example.accountservice.dto;

import com.example.accountservice.model.enums.AccountType;
import com.example.accountservice.model.enums.CurrencyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAccountRequest {

    @NotBlank
    private String customerId;

    @NotBlank
    private String accountHolderName;

    @NotNull
    private AccountType accountType;

    @NotBlank
    private CurrencyType currency;
}
