package com.exchange.service.impl;

import com.exchange.model.ErrorCode;
import com.exchange.model.Result;
import com.exchange.model.Transaction;
import com.exchange.repository.AccountRepository;
import com.exchange.repository.ExchangeRateRepository;
import com.exchange.repository.TransactionRepository;
import com.exchange.repository.UserRepository;
import com.exchange.repository.entity.TransactionEntity;
import com.exchange.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    TransactionServiceImpl(TransactionRepository transactionRepository, ExchangeRateRepository exchangeRateRepository, AccountRepository accountRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.mapper = modelMapper;
    }

    public Result<Transaction> createTransaction(Transaction transaction) {
        if(transaction == null) {
            var result = new Result<Transaction>();
            result.setError(ErrorCode.BAD_REQUEST);

            return result;
        }

        var exchangeRate = exchangeRateRepository.findByBaseCurrencyAndTargetCurrency(transaction.getCurrencyFrom(), transaction.getCurrencyTo());

        Instant nowUtc = Instant.now();
        transaction.setCreatedAt(nowUtc);
        transaction.setUpdatedAt(nowUtc);
        transaction.setIsRefunded(false);
        transaction.setExchangeRate(exchangeRate.getRate());
        transaction.setAmountTo(transaction.getAmountFrom().multiply(exchangeRate.getRate()));

        var entity = mapper.map(transaction, TransactionEntity.class);
        var accountFrom = accountRepository.findByUserIdAndCurrency(transaction.getUserId(), transaction.getCurrencyFrom());
        var accountTo = accountRepository.findByUserIdAndCurrency(transaction.getUserId(), transaction.getCurrencyTo());

        accountFrom.setBalance(accountFrom.getBalance().subtract(transaction.getAmountFrom()));
        accountTo.setBalance(accountTo.getBalance().add(transaction.getAmountTo()));

        transactionRepository.save(entity);
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);

        var result = new Result<Transaction>();
        result.setData(transaction);
        return result;
    }

    @Override
    public Result<List<Transaction>> getTransactions(String email) {
        var user = userRepository.findByEmail(email);
        var transactionEntities = transactionRepository.findAllByUserId(user.getId());

        var transactions = transactionEntities.stream()
                .map(entity -> mapper.map(entity, Transaction.class))
                .toList();

        var result = new Result<List<Transaction>>();
        result.setData(transactions);
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
