package com.exchange.service;

import com.exchange.model.Account;
import com.exchange.model.Result;

import java.util.Currency;
import java.util.List;

public interface AccountService {
    Result<Account> createAccount(Account account);
    Result<List<Account>> getAccountsByUserEmail(String userEmail);
    Result<List<Currency>> getAvailableAccountsByUserEmail(String userEmail);
    Result<Account> updateAccount(Account account);
}
