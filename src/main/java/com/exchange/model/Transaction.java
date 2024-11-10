package com.exchange.model;

import java.math.BigDecimal;
import java.util.Currency;

public class Transaction {
    private long id;
    private Currency currencyFrom;
    private Currency currencyTo;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private BigDecimal exchangeRate;
    private Boolean isRefunded;
}
