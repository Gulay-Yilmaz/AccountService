package com.example.accountservice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MoneyTransferRequest {
    @NotBlank(message = "transfer.from.iban.required")
    private String fromIban;

    @NotBlank(message = "transfer.to.iban.required")
    private String toIban;

    @NotNull(message = "transfer.amount.required")
    @Positive(message = "transfer.amount.positive")
    private BigDecimal amount;
}
