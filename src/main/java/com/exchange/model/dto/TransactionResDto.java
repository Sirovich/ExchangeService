package com.exchange.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResDto {
    private long id;
    private Currency currencyFrom;
    private Currency currencyTo;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private BigDecimal exchangeRate;
    private Boolean isRefunded;
    private Instant createdAt;
    private Instant updatedAt;
    private String checkNumber;
}
