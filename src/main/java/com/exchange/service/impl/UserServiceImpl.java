package com.exchange.service.impl;

import com.exchange.model.ErrorCode;
import com.exchange.model.Result;
import com.exchange.model.User;
import com.exchange.repository.UserRepository;
import com.exchange.repository.entity.UserEntity;
import com.exchange.service.UserService;
import com.exchange.utils.PasswordHelper;
import org.modelmapper.ModelMapper;

import java.time.Instant;

public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper mapper;

    UserServiceImpl(UserRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Result<User> createUser(User user) {
        if (user == null) {
            var result = new Result<User>();
            result.setError(ErrorCode.BAD_REQUEST);

            return result;
        }

        var hashedPassword = PasswordHelper.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        var entity = mapper.map(user, UserEntity.class);
        Instant nowUtc = Instant.now();
        entity.setCreatedAt(nowUtc);
        entity.setUpdatedAt(nowUtc);

        repository.save(entity);

        var result = new Result<User>();
        result.setData(user);

        return result;
    }

    @Override
    public Result<User> updateUser(long id, User user) {
        if(!repository.existsById(id)) {
            var result = new Result<User>();
            result.setError(ErrorCode.USER_NOT_FOUND);

            return result;
        }

        user.setId(id);
        var entity = mapper.map(user, UserEntity.class);
        entity.setUpdatedAt(Instant.now());

        repository.save(entity);

        var result = new Result<User>();
        result.setData(user);

        return result;
    }

    @Override
    public Result<Boolean> deleteUser(long id) {
        if(!repository.existsById(id)) {
            var result = new Result<Boolean>();
            result.setError(ErrorCode.USER_NOT_FOUND);

            return result;
        }

        repository.deleteById(id);

        var result = new Result<Boolean>();
        result.setData(true);
        return result;
    }

    @Override
    public Result<User> login(String email, String password) {
        var userEntity = repository.findByEmail(email);

        if(userEntity == null) {
            var result = new Result<User>();
            result.setError(ErrorCode.USER_NOT_FOUND);

            return result;
        }

        var hashedPassword = PasswordHelper.hashPassword(password);
        if(!hashedPassword.equals(userEntity.getPassword())) {
            var result = new Result<User>();
            result.setError(ErrorCode.INVALID_PASSWORD);

            return result;
        }

        var user = mapper.map(userEntity, User.class);

        var result = new Result<User>();
        result.setData(user);
        return result;
    }
}
