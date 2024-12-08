package com.exchange.controller;

import com.exchange.model.ExchangeRate;
import com.exchange.model.Rate;
import com.exchange.model.dto.ExchangeRateReqDto;
import com.exchange.service.ExchangeRateService;
import com.exchange.utils.ErrorHelper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;

@RequestMapping("api/rates")
@RestController
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final ModelMapper mapper;

    ExchangeRateController(ExchangeRateService exchangeRateService, ModelMapper mapper) {
        this.exchangeRateService = exchangeRateService;
        this.mapper = mapper;
    }

    @PostMapping("rate")
    public ResponseEntity<Void> createExchangeRate(@RequestBody ExchangeRateReqDto exchangeRateDto) {
        var exchangeRate = mapper.map(exchangeRateDto, ExchangeRate.class);

        var result = exchangeRateService.createExchangeRate(exchangeRate);

        if(!result.isSuccess()) {
            var httpStatus = ErrorHelper.processError(result.getError());
            return new ResponseEntity<Void>(httpStatus);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("list")
    public ResponseEntity<List<Rate>> getRatesByBaseCurrencies(@RequestParam List<Currency> baseCurrencies) {
        var result = exchangeRateService.getRatesByBaseCurrencies(baseCurrencies);

        if(!result.isSuccess()) {
            var httpStatus = ErrorHelper.processError(result.getError());
            return new ResponseEntity<List<Rate>>(httpStatus);
        }

        return new ResponseEntity<List<Rate>>(result.getData(), HttpStatus.OK);
    }
}
