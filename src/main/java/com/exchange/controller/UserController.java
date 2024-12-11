package com.exchange.controller;

import com.exchange.model.User;
import com.exchange.model.dto.*;
import com.exchange.utils.JwtHelper;
import com.exchange.service.UserService;
import com.exchange.utils.ErrorHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/users")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;
    private final JwtHelper jwtHelper;

    UserController(UserService userService, ModelMapper mapper, JwtHelper jwtHelper) {
        this.userService = userService;
        this.mapper = mapper;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping(value = "user", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<UserResDto> getUser(HttpServletRequest request) {
        var userEmail = jwtHelper.getUserEmailFromRequest(request);
        var result = userService.getUser(userEmail);

        if(!result.isSuccess()){
            var httpStatus = ErrorHelper.processError(result.getError());
            return new ResponseEntity<UserResDto>((UserResDto)null, httpStatus);
        }

        var userResDto = mapper.map(result.getData(), UserResDto.class);
        return new ResponseEntity<UserResDto>(userResDto, HttpStatus.OK);
    }

    @PostMapping(value = "user", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
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
    @CrossOrigin(origins = "http://localhost:3000")
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
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        var result = userService.deleteUser(id);

        if(!result.isSuccess()){
            var httpStatus = ErrorHelper.processError(result.getError());
            return ResponseEntity.status(httpStatus).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<LoginResDto> login(LoginDto loginDto) {
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

    @PostMapping(value = "register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<LoginResDto> register(RegisterDto registerDto) {
        var user = this.mapper.map(registerDto, User.class);
        var userResult = userService.createUser(user);

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
