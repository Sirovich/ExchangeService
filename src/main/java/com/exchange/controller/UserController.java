package com.exchange.controller;

import com.exchange.model.User;
import com.exchange.model.dto.UserReqDto;
import com.exchange.service.UserService;
import com.exchange.utils.ErrorHelper;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController("users")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "user", produces = "application/json")
    public void createUser(@RequestBody UserReqDto userDto) {
        ModelMapper mapper = new ModelMapper();
        var user = mapper.map(userDto, User.class);

        var result = userService.createUser(user);

        if(!result.isSuccess()){
            var httpStatus = ErrorHelper.processError(result.getError());

        }
    }
}
