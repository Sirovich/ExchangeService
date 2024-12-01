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
public class ExchangeRateReqDto {
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;
}
