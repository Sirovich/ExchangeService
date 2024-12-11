package com.exchange.service;

import com.exchange.model.Result;
import com.exchange.model.User;
import com.exchange.model.UserType;

public interface UserService {
    Result<User> createUser(User user);
    Result<User> updateUser(long id, User user);
    Result<Boolean> deleteUser(long id);
    Result<User> login(String email, String password);
    Result<User> getUser(String email);
}
