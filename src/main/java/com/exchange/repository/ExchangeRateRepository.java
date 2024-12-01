package com.exchange.repository;

import com.exchange.repository.entity.ExchangeRateEntity;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Currency;

@Repository
@Table(name = "exchangeRate")
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long> {
    ExchangeRateEntity findByBaseCurrencyAndTargetCurrency(Currency baseCurrency, Currency targetCurrency);
}
