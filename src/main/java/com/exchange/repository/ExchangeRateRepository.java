package com.exchange.repository;

import com.exchange.repository.entity.ExchangeRateEntity;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Currency;
import java.util.List;

@Repository
@Table(name = "exchangeRate")
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long> {
    ExchangeRateEntity findByBaseCurrencyAndTargetCurrency(Currency baseCurrency, Currency targetCurrency);

    @Modifying
    @Query("SELECT e FROM ExchangeRateEntity e WHERE e.baseCurrency IN :baseCurrencies")
    List<ExchangeRateEntity> findByBaseCurrencies(@Param("baseCurrencies") List<Currency> baseCurrencies);

    @Modifying
    @Query("SELECT DISTINCT e.baseCurrency AS currency FROM ExchangeRateEntity e UNION SELECT DISTINCT e.targetCurrency AS currency FROM ExchangeRateEntity e")
    List<Currency> findUniquesCurrencies();
}
