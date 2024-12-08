package com.exchange.service.impl;

import com.exchange.model.Account;
import com.exchange.model.ErrorCode;
import com.exchange.model.Result;
import com.exchange.repository.AccountRepository;
import com.exchange.repository.ExchangeRateRepository;
import com.exchange.repository.UserRepository;
import com.exchange.repository.entity.AccountEntity;
import com.exchange.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Currency;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ModelMapper mapper;

    AccountServiceImpl(UserRepository userRepository, AccountRepository accountRepository, ExchangeRateRepository exchangeRateRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.mapper = mapper;
    }

    @Override
    public Result<Account> createAccount(Account account) {
        if (account == null) {
            var result = new Result<Account>();
            result.setError(ErrorCode.BAD_REQUEST);

            return result;
        }

        var entity = mapper.map(account, AccountEntity.class);
        var nowTime = Instant.now();
        entity.setCreatedAt(nowTime);
        entity.setUpdatedAt(nowTime);

        entity = accountRepository.save(entity);

        var result = new Result<Account>();
        account = mapper.map(entity, Account.class);
        result.setData(account);

        return result;
    }

    @Override
    public Result<List<Account>> getAccountsByUserEmail(String userEmail) {
        var user = userRepository.findByEmail(userEmail);
        var accounts = accountRepository.findByUserId(user.getId());

        var result = new Result<List<Account>>();
        var accountList = accounts.stream()
                .map(accountEntity -> mapper.map(accountEntity, Account.class)).toList();
        result.setData(accountList);

        return result;
    }

    @Override
    public Result<List<Currency>> getAvailableAccountsByUserEmail(String userEmail) {
        var user = userRepository.findByEmail(userEmail);
        var rates = exchangeRateRepository.findUniquesCurrencies();
        var accounts = accountRepository.findByUserId(user.getId());

        var result = new Result<List<Currency>>();
        var currenciesList = rates.stream().filter(rate -> accounts.stream().noneMatch(account -> account.getCurrency() == rate)).toList();

        result.setData(currenciesList);
        return result;
    }
}
