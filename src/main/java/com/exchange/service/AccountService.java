package com.exchange.service;

import com.exchange.model.Account;
import com.exchange.model.Result;

public interface AccountService {
    Result<Account> createAccount(Account account);
}
