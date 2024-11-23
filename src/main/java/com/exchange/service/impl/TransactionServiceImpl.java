package com.exchange.service.impl;

import com.exchange.model.ErrorCode;
import com.exchange.model.Result;
import com.exchange.model.Transaction;
import com.exchange.repository.TransactionRepository;
import com.exchange.repository.entity.TransactionEntity;
import com.exchange.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ModelMapper mapper;

    TransactionServiceImpl(TransactionRepository transactionRepository, ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.mapper = modelMapper;
    }

    public Result<Transaction> createTransaction(Transaction transaction) {
        if(transaction == null) {
            var result = new Result<Transaction>();
            result.setError(ErrorCode.BAD_REQUEST);

            return result;
        }

        Instant nowUtc = Instant.now();
        transaction.setCreatedAt(nowUtc);
        transaction.setUpdatedAt(nowUtc);

        var entity = mapper.map(transaction, TransactionEntity.class);

        transactionRepository.save(entity);

        var result = new Result<Transaction>();
        result.setData(transaction);
        return result;
    }

    public Result<Void> refundTransaction(long id) {
        if(!transactionRepository.existsById(id)) {
            var result = new Result<Void>();
            result.setError(ErrorCode.TRANSACTION_NOT_FOUND);

            return result;
        }

        var updatedAt = Instant.now();
        transactionRepository.updateIsRefundedAndUpdatedAtById(id, true, updatedAt);

        return new Result<Void>();
    }
}
