package com.exchange.repository;

import com.exchange.repository.entity.TransactionEntity;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@Table(name = "transaction")
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    @Modifying
    @Query("UPDATE TransactionEntity t SET t.isRefunded = :isRefunded, t.updatedAt = :updatedAt WHERE t.id = :id")
    int updateIsRefundedAndUpdatedAtById(@Param("id") long id, @Param("isRefunded") boolean isRefunded, @Param("updatedAt") Instant updatedAt);

    List<TransactionEntity> findAllByUserId(long userId);
}
