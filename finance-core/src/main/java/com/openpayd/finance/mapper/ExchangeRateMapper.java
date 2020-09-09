package com.openpayd.finance.mapper;

import com.openpayd.finance.api.request.RatesApiRequest;
import com.openpayd.finance.api.response.RatesApiResponse;
import com.openpayd.finance.controller.request.ExchangeRateRequest;
import com.openpayd.finance.controller.response.ExchangeRateResponse;
import com.openpayd.finance.entity.Exchange;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {

    ExchangeRateRequest mapRequest(RatesApiRequest source);

    ExchangeRateResponse mapResponse(RatesApiResponse source);

    RatesApiRequest mapRequest(ExchangeRateRequest source);

    RatesApiResponse mapResponse(ExchangeRateResponse source);

    static List<Exchange> crossMapResponse(final ExchangeRateResponse exchangeRateResponse,
                                           final BigDecimal amount, final Currency base, final LocalDate date,
                                           final BiFunction<BigDecimal, BigDecimal, BigDecimal> calculationMethod,
                                           final LocalDateTime now) {
        return exchangeRateResponse.getRates().entrySet().stream()
                .map(entry -> {
                    Currency symbol = entry.getKey();
                    BigDecimal rate = entry.getValue();

                    return Exchange.builder()
                            .base(base)
                            .date(date)
                            .amount(amount)
                            .symbol(symbol)
                            .rate(rate)
                            .calculated(calculationMethod.apply(amount, rate))
                            .transactionDate(now)
                            .build();
                }).collect(Collectors.toList());
    }

}
