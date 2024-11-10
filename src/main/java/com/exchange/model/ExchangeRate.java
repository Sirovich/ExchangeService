package com.exchange.model;

import java.math.BigDecimal;
import java.util.Currency;

public class ExchangeRate {
    private long id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;
}
