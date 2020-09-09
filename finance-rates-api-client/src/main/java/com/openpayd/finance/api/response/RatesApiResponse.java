package com.openpayd.finance.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatesApiResponse {

    private Currency base;
    private Map<Currency, BigDecimal> rates;
    private LocalDate date;

}
