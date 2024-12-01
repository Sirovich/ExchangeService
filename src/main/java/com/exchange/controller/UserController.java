package com.exchange.controller;

import com.exchange.model.User;
import com.exchange.model.dto.LoginDto;
import com.exchange.model.dto.LoginResDto;
import com.exchange.utils.JwtHelper;
import com.exchange.model.dto.UserReqDto;
import com.exchange.model.dto.UserResDto;
import com.exchange.service.UserService;
import com.exchange.utils.ErrorHelper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/users")
@RestController
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;
    private final JwtHelper jwtHelper;

    UserController(UserService userService, ModelMapper mapper, JwtHelper jwtHelper) {
        this.userService = userService;
        this.mapper = mapper;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping(value = "user/{id}", produces = "application/json")
    public ResponseEntity<UserResDto> getUser(@PathVariable long id) {
        var result = userService.getUser(id);

        if(!result.isSuccess()){
            var httpStatus = ErrorHelper.processError(result.getError());
            return new ResponseEntity<UserResDto>((UserResDto)null, httpStatus);
        }

        var userResDto = mapper.map(result.getData(), UserResDto.class);
        return new ResponseEntity<UserResDto>(userResDto, HttpStatus.OK);
    }

    @PostMapping(value = "user", produces = "application/json")
    public ResponseEntity<String> createUser(@RequestBody UserReqDto userDto) {
        var user = mapper.map(userDto, User.class);

        var result = userService.createUser(user);

        if(!result.isSuccess()){
            var httpStatus = ErrorHelper.processError(result.getError());
            return new ResponseEntity<String>("", httpStatus);
        }

        var jwt = jwtHelper.generateToken(result.getData());
        return new ResponseEntity<String>(jwt, HttpStatus.OK);
    }

    @PutMapping(value = "user/{id}", produces = "application/json")
    public ResponseEntity<UserResDto> updateUser(@RequestBody UserReqDto userDto, @PathVariable long id) {
        var user = mapper.map(userDto, User.class);

        var result = userService.updateUser(id, user);

        if(!result.isSuccess()){
            var httpStatus = ErrorHelper.processError(result.getError());
            return new ResponseEntity<UserResDto>((UserResDto)null, httpStatus);
        }

        var userResDto = mapper.map(result.getData(), UserResDto.class);
        return new ResponseEntity<UserResDto>(userResDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        var result = userService.deleteUser(id);

        if(!result.isSuccess()){
            var httpStatus = ErrorHelper.processError(result.getError());
            return ResponseEntity.status(httpStatus).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "login")
    public ResponseEntity<LoginResDto> login(@RequestBody LoginDto loginDto) {
        var userResult = userService.login(loginDto.getEmail(), loginDto.getPassword());

        if(!userResult.isSuccess()){
            var httpStatus = ErrorHelper.processError(userResult.getError());
            return new ResponseEntity<LoginResDto>((LoginResDto)null, httpStatus);
        }

        var jwt = jwtHelper.generateToken(userResult.getData());
        var loginResult = this.mapper.map(userResult.getData(), LoginResDto.class);
        loginResult.setToken(jwt);

        return new ResponseEntity<LoginResDto>(loginResult, HttpStatus.OK);
    }
}
