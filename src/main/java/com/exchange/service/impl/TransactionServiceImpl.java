package com.exchange.service.impl;

import com.exchange.model.ErrorCode;
import com.exchange.model.Result;
import com.exchange.model.Transaction;
import com.exchange.repository.AccountRepository;
import com.exchange.repository.ExchangeRateRepository;
import com.exchange.repository.TransactionRepository;
import com.exchange.repository.UserRepository;
import com.exchange.repository.entity.AccountEntity;
import com.exchange.repository.entity.TransactionEntity;
import com.exchange.service.TransactionService;
import com.exchange.utils.ReceiptHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
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

        Instant nowUtc = Instant.now();
        transaction.setCheckNumber(ReceiptHelper.generateRandomCheckNumber());
        transaction.setCreatedAt(nowUtc);
        transaction.setUpdatedAt(nowUtc);
        transaction.setIsRefunded(false);

        var entity = mapper.map(transaction, TransactionEntity.class);
        var accountFrom = accountRepository.findByUserIdAndCurrency(transaction.getUserId(), transaction.getCurrencyFrom());
        var accountTo = accountRepository.findByUserIdAndCurrency(transaction.getUserId(), transaction.getCurrencyTo());

        accountFrom.setBalance(accountFrom.getBalance().subtract(transaction.getAmountFrom()));

        if(accountTo == null) {
            accountTo = new AccountEntity();
            accountTo.setUserId(transaction.getUserId());
            accountTo.setBalance(transaction.getAmountTo());
            accountTo.setCurrency(transaction.getCurrencyTo());
            accountTo.setCreatedAt(nowUtc);
            accountTo.setUpdatedAt(nowUtc);
            accountRepository.save(accountTo);
        }
        else {
            accountTo.setBalance(accountTo.getBalance().add(transaction.getAmountTo()));
        }

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
        transactionEntities.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));

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
