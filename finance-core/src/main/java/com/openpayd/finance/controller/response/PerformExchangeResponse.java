package com.openpayd.finance.controller.response;

import com.openpayd.finance.dto.ExchangeDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformExchangeResponse {

    private Currency base;
    private BigDecimal amount;
    private List<ExchangeDetailDto> detailsList;
    private LocalDate date;
    private LocalDateTime transactionDate;

}
