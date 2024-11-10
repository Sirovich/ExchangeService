package com.exchange.service;

import com.exchange.model.Result;
import com.exchange.model.Transaction;

public interface TransactionService {
    Result createTransaction(Transaction transaction);
    Result refundTransaction(long id);
}
