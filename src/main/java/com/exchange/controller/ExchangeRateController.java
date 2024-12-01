package com.exchange.controller;

import com.exchange.model.ExchangeRate;
import com.exchange.model.dto.ExchangeRateReqDto;
import com.exchange.service.ExchangeRateService;
import com.exchange.utils.ErrorHelper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
