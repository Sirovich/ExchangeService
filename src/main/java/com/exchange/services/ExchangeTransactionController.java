package com.exchange.services;

import com.exchange.domain.services.ExchangeTransactionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeTransactionController {

    @Autowired
    private ExchangeTransactionServiceInterface transactionService;


}
