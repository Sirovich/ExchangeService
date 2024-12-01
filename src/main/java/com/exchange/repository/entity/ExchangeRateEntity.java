package com.exchange.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "exchangeRate")
public class ExchangeRateEntity extends BaseEntity{
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;
}
