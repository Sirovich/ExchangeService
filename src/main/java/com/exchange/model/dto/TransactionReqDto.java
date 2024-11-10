package com.exchange.model.dto;

import java.math.BigDecimal;
import java.util.Currency;

public class TransactionReqDto {
    private Currency currencyFrom;
    private Currency currencyTo;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private BigDecimal exchangeRate;
    private Boolean isRefunded;
}
