package com.openpayd.finance;

import com.openpayd.finance.api.request.RatesApiRequest;
import com.openpayd.finance.api.response.RatesApiResponse;
import com.openpayd.finance.controller.request.ExchangeRateRequest;
import com.openpayd.finance.controller.request.PerformExchangeRequest;
import com.openpayd.finance.controller.response.ExchangeRateResponse;
import com.openpayd.finance.controller.response.PerformExchangeResponse;
import com.openpayd.finance.entity.Exchange;
import org.apache.commons.collections4.map.HashedMap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

public class TestBase {

    public static final String ID = "ID";
    public static final LocalDate DATE = LocalDate.of(2011,11,11);
    public static final LocalDateTime DATE_TIME = LocalDateTime.of(2011,11,11,11,11);
    public static final Currency TRY = Currency.getInstance("TRY");
    public static final Currency USD = Currency.getInstance("USD");
    public static final BigDecimal TRY_RATE = BigDecimal.ONE;
    public static final BigDecimal AMOUNT = BigDecimal.ONE;

    public List<Currency> createSymbols() {
        List<Currency> symbols = new ArrayList<>();
        symbols.add(TRY);

        return symbols;
    }

    public RatesApiRequest createRatesApiRequest() {
        return RatesApiRequest.builder()
                .base(USD)
                .symbols(createSymbols())
                .build();
    }

    public ExchangeRateRequest createExchangeRateRequest() {
        return ExchangeRateRequest.builder()
                .base(USD)
                .symbols(createSymbols())
                .build();
    }

    public Map<Currency, BigDecimal> createRates() {
        Map<Currency, BigDecimal> rates = new HashedMap<>();
        rates.put(TRY, TRY_RATE);

        return rates;
    }

    public RatesApiResponse createRatesApiResponse() {
        return RatesApiResponse.builder()
                .base(USD)
                .rates(createRates())
                .date(DATE)
                .build();
    }

    public ExchangeRateResponse createExchangeRateResponse() {
        return ExchangeRateResponse.builder()
                .base(USD)
                .rates(createRates())
                .date(DATE)
                .build();
    }

    public PerformExchangeRequest createPerformExchangeRequest() {
        return PerformExchangeRequest.builder()
                .base(USD)
                .symbols(createSymbols())
                .amount(AMOUNT)
                .build();
    }

    public PerformExchangeResponse createPerformExchangeResponse() {
        return PerformExchangeResponse.builder()
                .build();
    }
    
    public Exchange createExchange() {
        return Exchange.builder()
                .transactionDate(DATE_TIME)
                .calculated(AMOUNT.multiply(TRY_RATE))
                .rate(TRY_RATE)
                .amount(AMOUNT)
                .symbol(TRY)
                .base(USD)
                .date(DATE)
                .id(ID)
                .build();
    }


}
