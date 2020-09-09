package com.openpayd.finance.service.impl;

import com.openpayd.finance.api.RatesApiClient;
import com.openpayd.finance.api.response.RatesApiResponse;
import com.openpayd.finance.controller.request.ExchangeRateRequest;
import com.openpayd.finance.controller.response.ExchangeRateResponse;
import com.openpayd.finance.mapper.ExchangeRateMapper;
import com.openpayd.finance.service.ExchangeRateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@AllArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final RatesApiClient ratesApiClient;
    private final ExchangeRateMapper mapper;

    public ExchangeRateResponse getExchangeRates(Currency base, List<Currency> symbols) {
        return getExchangeRates(ExchangeRateRequest.builder()
                .base(base)
                .symbols(symbols)
                .build());
    }

    public ExchangeRateResponse getExchangeRates(ExchangeRateRequest request) {
        log.info("getExchangeRates-begin {}", kv("request", request));

        RatesApiResponse response = ratesApiClient.getRates(mapper.mapRequest(request));

        log.info("getExchangeRates-end {}", kv("response", response));
        return mapper.mapResponse(response);
    }

}
