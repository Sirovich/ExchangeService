package com.exchange.controller;

import com.exchange.model.Account;
import com.exchange.model.dto.AccountReqDto;
import com.exchange.service.AccountService;
import com.exchange.utils.ErrorHelper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/accounts")
@RestController
public class AccountController {
    private final AccountService accountService;
    private final ModelMapper mapper;

    AccountController(AccountService accountService, ModelMapper mapper) {
        this.accountService = accountService;
        this.mapper = mapper;
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
}
