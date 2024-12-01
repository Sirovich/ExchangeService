package com.exchange.service.impl;

import com.exchange.model.ErrorCode;
import com.exchange.model.Result;
import com.exchange.model.User;
import com.exchange.repository.AccountRepository;
import com.exchange.repository.UserRepository;
import com.exchange.repository.entity.AccountEntity;
import com.exchange.repository.entity.UserEntity;
import com.exchange.service.UserService;
import com.exchange.utils.PasswordHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    UserServiceImpl(UserRepository repository, AccountRepository accountRepository, ModelMapper mapper) {
        this.userRepository = repository;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public Result<User> createUser(User user) {
        if (user == null) {
            var result = new Result<User>();
            result.setError(ErrorCode.BAD_REQUEST);

            return result;
        }

        if(userRepository.existsByEmail(user.getEmail())) {
            var result = new Result<User>();
            result.setError(ErrorCode.EMAIL_ALREADY_EXISTS);

            return result;
        }

        var hashedPassword = PasswordHelper.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        var entity = mapper.map(user, UserEntity.class);
        Instant nowUtc = Instant.now();
        entity.setCreatedAt(nowUtc);
        entity.setUpdatedAt(nowUtc);
        entity = userRepository.save(entity);
        user = mapper.map(entity, User.class);

        var accountEntity = new AccountEntity();
        accountEntity.setUserId(user.getId());
        accountEntity.setBalance(new BigDecimal(100));
        accountEntity.setCurrency(Currency.getInstance("BYN"));
        accountEntity.setCreatedAt(nowUtc);
        accountEntity.setUpdatedAt(nowUtc);
        accountRepository.save(accountEntity);

        var result = new Result<User>();
        result.setData(user);

        return result;
    }

    @Override
    public Result<User> updateUser(long id, User user) {
        if(!userRepository.existsById(id)) {
            var result = new Result<User>();
            result.setError(ErrorCode.USER_NOT_FOUND);

            return result;
        }

        user.setId(id);
        var entity = mapper.map(user, UserEntity.class);
        entity.setUpdatedAt(Instant.now());

        userRepository.save(entity);

        var result = new Result<User>();
        result.setData(user);

        return result;
    }

    @Override
    public Result<Boolean> deleteUser(long id) {
        if(!userRepository.existsById(id)) {
            var result = new Result<Boolean>();
            result.setError(ErrorCode.USER_NOT_FOUND);

            return result;
        }

        userRepository.deleteById(id);

        var result = new Result<Boolean>();
        result.setData(true);
        return result;
    }

    @Override
    public Result<User> login(String email, String password) {
        var userEntity = userRepository.findByEmail(email);

        if(userEntity == null) {
            var result = new Result<User>();
            result.setError(ErrorCode.WRONG_CREDENTIALS);

            return result;
        }

        if(!PasswordHelper.checkPassword(password, userEntity.getPassword())) {
            var result = new Result<User>();
            result.setError(ErrorCode.WRONG_CREDENTIALS);

            return result;
        }

        var user = mapper.map(userEntity, User.class);

        var result = new Result<User>();
        result.setData(user);
        return result;
    }

    @Override
    public Result<User> getUser(long id) {
        var entity = userRepository.findById(id);

        if(entity.isEmpty()) {
            var result = new Result<User>();
            result.setError(ErrorCode.USER_NOT_FOUND);

            return result;
        }

        var user = mapper.map(entity.get(), User.class);

        var result = new Result<User>();
        result.setData(user);
        return result;
    }
}
