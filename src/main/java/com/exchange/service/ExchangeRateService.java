package com.exchange.service;

import com.exchange.model.ExchangeRate;
import com.exchange.model.Result;

public interface ExchangeRateService {
    Result<Void> createExchangeRate(ExchangeRate exchangeRate);
}
