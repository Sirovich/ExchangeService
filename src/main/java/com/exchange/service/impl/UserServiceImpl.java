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

    UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result createUser(User user) {
        if (user == null) {
            var result = new Result();
            result.setError(ErrorCode.BAD_REQUEST);

            return result;
        }

        var hashedPassword = PasswordHelper.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        ModelMapper mapper = new ModelMapper();
        var entity = mapper.map(user, UserEntity.class);
        Instant nowUtc = Instant.now();
        entity.setCreatedAt(nowUtc);
        entity.setUpdatedAt(nowUtc);

        repository.save(entity);

        return new Result();
    }

    @Override
    public Result updateUser(long id, User user) {
        if(!repository.existsById(id)) {
            var result = new Result();
            result.setError(ErrorCode.USER_NOT_FOUND);

            return result;
        }

        user.setId(id);
        ModelMapper mapper = new ModelMapper();
        var entity = mapper.map(user, UserEntity.class);
        entity.setUpdatedAt(Instant.now());

        repository.save(entity);

        return new Result();
    }

    @Override
    public Result deleteUser(long id) {
        if(!repository.existsById(id)) {
            var result = new Result();
            result.setError(ErrorCode.USER_NOT_FOUND);

            return result;
        }

        repository.deleteById(id);
        return new Result();
    }
}
