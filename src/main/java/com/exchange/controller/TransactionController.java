package com.exchange.controller;

import com.exchange.model.User;
import com.exchange.model.dto.TransactionReqDto;
import com.exchange.model.Transaction;
import com.exchange.service.TransactionService;
import com.exchange.utils.ErrorHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("transactions")
public class TransactionController {
    private final TransactionService transactionService;

    TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("transaction")
    public void createTransaction(@RequestBody TransactionReqDto transactionDto) {
        ModelMapper mapper = new ModelMapper();
        var transaction = mapper.map(transactionDto, Transaction.class);

        var result = transactionService.createTransaction(transaction);

        if(!result.isSuccess()) {
            var httpStatus = ErrorHelper.processError(result.getError());
        }
    }

    @DeleteMapping("transaction/{id}")
    public void refundTransaction(@PathVariable long id) {
        var result = transactionService.refundTransaction(id);

        if(!result.isSuccess()) {
            var httpStatus = ErrorHelper.processError(result.getError());
        }
    }
}
