package com.exchange.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionReqDto {
    private long userId;
    private Currency currencyFrom;
    private Currency currencyTo;
    private BigDecimal amountFrom;
}
