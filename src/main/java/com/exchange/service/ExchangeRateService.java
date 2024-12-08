package com.exchange.service;

import com.exchange.model.ExchangeRate;
import com.exchange.model.Rate;
import com.exchange.model.Result;

import java.util.Currency;
import java.util.List;

public interface ExchangeRateService {
    Result<Void> createExchangeRate(ExchangeRate exchangeRate);
    Result<List<Rate>> getRatesByBaseCurrencies(List<Currency> baseCurrencies);
}
