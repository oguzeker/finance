package com.openpayd.finance.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformExchangeRequest {

    private Currency base;
    private List<Currency> symbols;
    private BigDecimal amount;

}
