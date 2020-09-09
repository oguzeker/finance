package com.openpayd.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDetailDto {

    private String transactionId;
    private Currency symbol;
    private BigDecimal rate;
    private BigDecimal calculated;

}
