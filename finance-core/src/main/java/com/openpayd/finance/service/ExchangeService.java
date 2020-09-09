package com.openpayd.finance.service;

import com.openpayd.finance.controller.request.PerformExchangeRequest;
import com.openpayd.finance.controller.response.GetExchangeResponse;
import com.openpayd.finance.controller.response.PerformExchangeResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface ExchangeService {

    PerformExchangeResponse performExchange(PerformExchangeRequest request);

    GetExchangeResponse getById(String id);

    Page<GetExchangeResponse> getAll(int pageIndex, int pageSize);

    Page<GetExchangeResponse> getAllByTransactionDateBetween(LocalDateTime beginDate, LocalDateTime endDate,
                                                             int pageIndex, int pageSize);

}
