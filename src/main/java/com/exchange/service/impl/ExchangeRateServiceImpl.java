package com.exchange.service.impl;

import com.exchange.model.ErrorCode;
import com.exchange.model.ExchangeRate;
import com.exchange.model.Rate;
import com.exchange.model.Result;
import com.exchange.repository.ExchangeRateRepository;
import com.exchange.repository.entity.ExchangeRateEntity;
import com.exchange.service.ExchangeRateService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final ModelMapper mapper;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository, ModelMapper mapper) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.mapper = mapper;
    }

    @Override
    public Result<Void> createExchangeRate(ExchangeRate exchangeRate) {
        if (exchangeRate == null) {
            var result = new Result<Void>();
            result.setError(ErrorCode.BAD_REQUEST);

            return result;
        }

        var entity = mapper.map(exchangeRate, ExchangeRateEntity.class);
        var dateNow = Instant.now();
        entity.setCreatedAt(dateNow);
        entity.setUpdatedAt(dateNow);

        exchangeRateRepository.save(entity);
        return new Result<Void>();
    }

    @Override
    public Result<List<Rate>> getRatesByBaseCurrencies(List<Currency> baseCurrencies) {
        if (baseCurrencies == null) {
            var result = new Result<List<Rate>>();
            result.setError(ErrorCode.BAD_REQUEST);

            return result;
        }

        var entities = exchangeRateRepository.findByBaseCurrencies(baseCurrencies);
        var exchangeRates = entities.stream()
                .map(entity -> mapper.map(entity, ExchangeRate.class))
                .toList();

        var rateList = new ArrayList<Rate>();
        exchangeRates.stream().collect(Collectors.groupingBy(ExchangeRate::getBaseCurrency))
                .forEach((baseCurrency, rates) -> {
                    var rate = new Rate();
                    rate.setBaseCurrency(baseCurrency);
                    rate.setRates(rates.stream().collect(Collectors.toMap(ExchangeRate::getTargetCurrency, ExchangeRate::getRate)));
                    rateList.add(rate);
                });

        var result = new Result<List<Rate>>();
        result.setData(rateList);

        return result;
    }
}
