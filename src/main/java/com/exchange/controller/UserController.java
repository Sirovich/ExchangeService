package com.exchange.controller;

import com.exchange.model.User;
import com.exchange.model.dto.UserReqDto;
import com.exchange.model.dto.UserResDto;
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

    @PutMapping(value = "user/{id}", produces = "application/json")
    public void updateUser(@RequestBody UserReqDto userDto, @PathVariable long id) {
        ModelMapper mapper = new ModelMapper();
        var user = mapper.map(userDto, User.class);

        var result = userService.updateUser(id, user);

        if(!result.isSuccess()){
            var httpStatus = ErrorHelper.processError(result.getError());
        }
    }

    @DeleteMapping(value = "user/{id}")
    public void deleteUser(@PathVariable long id) {
        var result = userService.deleteUser(id);

        if(!result.isSuccess()){
            var httpStatus = ErrorHelper.processError(result.getError());
        }
    }

    @PostMapping(value = "login")
    public UserResDto login(@RequestBody String email, @RequestBody String password) {

    }
}
