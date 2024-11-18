package com.exchange.repository;

import com.exchange.repository.entity.ExchangeRate;
import com.exchange.repository.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    TransactionEntity updateIsRefundedAndUpdatedAtById(long id, boolean isRefunded, Instant updatedAt);
}
