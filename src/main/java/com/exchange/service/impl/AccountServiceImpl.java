package com.exchange.service.impl;

import com.exchange.model.Account;
import com.exchange.model.ErrorCode;
import com.exchange.model.Result;
import com.exchange.repository.AccountRepository;
import com.exchange.repository.entity.AccountEntity;
import com.exchange.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    AccountServiceImpl(AccountRepository accountRepository, ModelMapper mapper) {
        this.accountRepository = accountRepository;
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
}
