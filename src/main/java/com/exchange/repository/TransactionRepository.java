package com.exchange.repository;

import com.exchange.repository.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<ExchangeRate, Long> {
}
