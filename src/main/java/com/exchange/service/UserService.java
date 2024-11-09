package com.exchange.service;

import com.exchange.model.Result;
import com.exchange.model.User;

public interface UserService {
    Result createUser(User user);
    Result updateUser(User user);
    Result deleteUser(long id);
}