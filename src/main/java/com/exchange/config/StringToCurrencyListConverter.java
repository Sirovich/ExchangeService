package com.exchange.config;

import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

public class StringToCurrencyListConverter implements Converter<String, List<Currency>> {
    @Override
    public List<Currency> convert(String source) {
        return Arrays.stream(source.split(","))
                .map(Currency::getInstance)
                .collect(Collectors.toList());
    }
}
