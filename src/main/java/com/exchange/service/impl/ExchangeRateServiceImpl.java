package com.exchange.service.impl;

import com.exchange.model.ErrorCode;
import com.exchange.model.ExchangeRate;
import com.exchange.model.Result;
import com.exchange.repository.ExchangeRateRepository;
import com.exchange.repository.entity.ExchangeRateEntity;
import com.exchange.service.ExchangeRateService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;

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
}
