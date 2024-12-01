package com.exchange.service;

import com.exchange.model.Result;
import com.exchange.model.Transaction;

import java.util.List;

public interface TransactionService {
    Result<Transaction> createTransaction(Transaction transaction);
    Result<List<Transaction>> getTransactions(String email);
    Result<Void> refundTransaction(long id);
}
