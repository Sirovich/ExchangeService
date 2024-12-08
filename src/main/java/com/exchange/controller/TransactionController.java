package com.exchange.controller;

import com.exchange.model.User;
import com.exchange.model.dto.TransactionReqDto;
import com.exchange.model.Transaction;
import com.exchange.model.dto.TransactionResDto;
import com.exchange.service.TransactionService;
import com.exchange.utils.ErrorHelper;
import com.exchange.utils.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/transactions")
@RestController
public class TransactionController {
    private final TransactionService transactionService;
    private final ModelMapper mapper;
    private final JwtHelper jwtHelper;

    TransactionController(TransactionService transactionService, ModelMapper mapper, JwtHelper jwtHelper) {
        this.transactionService = transactionService;
        this.mapper = mapper;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping("list")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<TransactionResDto>> getTransactions(HttpServletRequest request) {
        var userEmail = jwtHelper.getUserEmailFromRequest(request);
        var result = transactionService.getTransactions(userEmail);

        if(!result.isSuccess()) {
            var httpStatus = ErrorHelper.processError(result.getError());
            return new ResponseEntity<List<TransactionResDto>>((List<TransactionResDto>)null, httpStatus);
        }

        var transactions = result.getData().stream()
            .map(transaction -> mapper.map(transaction, TransactionResDto.class))
            .toList();

        return new ResponseEntity<List<TransactionResDto>>(transactions, HttpStatus.OK);
    }

    @PostMapping("transaction")
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionReqDto transactionDto) {
        var transaction = mapper.map(transactionDto, Transaction.class);

        var result = transactionService.createTransaction(transaction);

        if(!result.isSuccess()) {
            var httpStatus = ErrorHelper.processError(result.getError());
            return new ResponseEntity<Void>(httpStatus);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("transaction/{id}")
    public ResponseEntity<Void> refundTransaction(@PathVariable long id) {
        var result = transactionService.refundTransaction(id);

        if(!result.isSuccess()) {
            var httpStatus = ErrorHelper.processError(result.getError());
            return new ResponseEntity<Void>(httpStatus);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
