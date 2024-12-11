package com.exchange.controller;

import com.exchange.model.Account;
import com.exchange.model.dto.AccountReqDto;
import com.exchange.service.AccountService;
import com.exchange.utils.ErrorHelper;
import com.exchange.utils.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;

@RequestMapping("api/accounts")
@RestController
public class AccountController {
    private final AccountService accountService;
    private final ModelMapper mapper;
    private final JwtHelper jwtHelper;

    AccountController(AccountService accountService, ModelMapper mapper, JwtHelper jwtHelper) {
        this.accountService = accountService;
        this.mapper = mapper;
        this.jwtHelper = jwtHelper;
    }

    @PostMapping("account")
    public ResponseEntity<Account> createAccount(@RequestBody AccountReqDto accountDto) {
        var account = mapper.map(accountDto, Account.class);
        account.setId((long)0);
        var result = accountService.createAccount(account);

        if (!result.isSuccess()) {
            var httpStatus = ErrorHelper.processError(result.getError());
            return new ResponseEntity<Account>(httpStatus);
        }

        return new ResponseEntity<Account>(result.getData(), HttpStatus.OK);
    }

    @GetMapping("list")
    public ResponseEntity<List<Account>> getAccounts(HttpServletRequest request) {
        var userEmail = jwtHelper.getUserEmailFromRequest(request);
        var result = accountService.getAccountsByUserEmail(userEmail);

        if (!result.isSuccess()) {
            var httpStatus = ErrorHelper.processError(result.getError());
            return new ResponseEntity<List<Account>>(httpStatus);
        }

        return new ResponseEntity<List<Account>>(result.getData(), HttpStatus.OK);
    }

    @GetMapping("currencies")
    public ResponseEntity<List<Currency>> getAvailableAccounts(HttpServletRequest request) {
        var userEmail = jwtHelper.getUserEmailFromRequest(request);
        var result = accountService.getAvailableAccountsByUserEmail(userEmail);

        if (!result.isSuccess()) {
            var httpStatus = ErrorHelper.processError(result.getError());
            return new ResponseEntity<List<Currency>>(httpStatus);
        }

        return new ResponseEntity<List<Currency>>(result.getData(), HttpStatus.OK);
    }

    @PostMapping("account/{id}")
    public ResponseEntity<Account> updateAccount(@RequestBody Account accountDto, @PathVariable long id) {
        var result = accountService.updateAccount(accountDto);

        if (!result.isSuccess()) {
            var httpStatus = ErrorHelper.processError(result.getError());
            return new ResponseEntity<Account>(httpStatus);
        }

        return new ResponseEntity<Account>(result.getData(), HttpStatus.OK);
    }
}
