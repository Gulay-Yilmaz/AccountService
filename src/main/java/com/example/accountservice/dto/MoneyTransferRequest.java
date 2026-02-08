package com.example.accountservice.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class MoneyTransferRequest {
    private String fromIban;
    private String toIban;
    private BigDecimal amount;
}
