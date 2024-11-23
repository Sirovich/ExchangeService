package com.exchange.service;

import com.exchange.model.Result;
import com.exchange.model.Transaction;

public interface TransactionService {
    Result<Transaction> createTransaction(Transaction transaction);
    Result<Void> refundTransaction(long id);
}
