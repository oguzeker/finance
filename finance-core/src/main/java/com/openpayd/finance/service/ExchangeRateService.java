package com.openpayd.finance.service;

import com.openpayd.finance.controller.request.ExchangeRateRequest;
import com.openpayd.finance.controller.response.ExchangeRateResponse;

import java.util.Currency;
import java.util.List;

public interface ExchangeRateService {

    ExchangeRateResponse getExchangeRates(Currency base, List<Currency> symbols);

    ExchangeRateResponse getExchangeRates(ExchangeRateRequest request);

}
