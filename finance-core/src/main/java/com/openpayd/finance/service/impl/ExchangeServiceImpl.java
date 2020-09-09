package com.openpayd.finance.service.impl;

import com.openpayd.finance.controller.request.PerformExchangeRequest;
import com.openpayd.finance.controller.response.ExchangeRateResponse;
import com.openpayd.finance.controller.response.GetExchangeResponse;
import com.openpayd.finance.controller.response.PerformExchangeResponse;
import com.openpayd.finance.dto.ExchangeDetailDto;
import com.openpayd.finance.entity.Exchange;
import com.openpayd.finance.mapper.ExchangeMapper;
import com.openpayd.finance.mapper.ExchangeRateMapper;
import com.openpayd.finance.repository.ExchangeRepository;
import com.openpayd.finance.service.ExchangeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;


@Slf4j
@Service
@AllArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeRateServiceImpl service;
    private final ExchangeRepository repository;
    private final ExchangeMapper mapper;

    @Transactional
    public PerformExchangeResponse performExchange(PerformExchangeRequest request) {
        log.info("performExchange-begin {}", kv("request", request));

        ExchangeRateResponse exchangeRateResponse = service.getExchangeRates(mapper.crossMapRequest(request));

        BigDecimal amount = request.getAmount();
        Currency base = exchangeRateResponse.getBase();
        LocalDate date = exchangeRateResponse.getDate();
        LocalDateTime now = LocalDateTime.now();

        List<Exchange> exchangeList = getExchangesFromExchangeRates(exchangeRateResponse, amount, base, date, now);
        List<ExchangeDetailDto> detailsList = persistExchangesAndGetExchangeDetails(exchangeList);

        PerformExchangeResponse response = PerformExchangeResponse.builder()
                .detailsList(detailsList)
                .date(date)
                .base(base)
                .amount(amount)
                .transactionDate(now)
                .build();

        log.info("performExchange-end {}", kv("response", response));
        return response;
    }

    public GetExchangeResponse getById(String id) {
        log.info("getById-begin {}", kv("id", id));

        GetExchangeResponse response = repository.findById(id)
                .map(mapper::mapEntityToResponse)
                .orElse(null);

        log.info("getById-end {}", kv("response", response));
        return response;
    }

    public Page<GetExchangeResponse> getAll(int pageIndex, int pageSize) {
        log.info("getAll-begin {} {}", kv("pageIndex", pageIndex), kv("pageSize", pageSize));

        Page<GetExchangeResponse> response = repository.findAll(PageRequest.of(pageIndex, pageSize))
                .map(mapper::mapEntityToResponse);

        log.info("getAll-end {}", kv("response", response));
        return response;
    }

    public Page<GetExchangeResponse> getAllByTransactionDateBetween(LocalDateTime beginDate, LocalDateTime endDate,
                                                                    int pageIndex, int pageSize) {
        log.info("getAllByTransactionDateBetween-begin {} {} {} {}", kv("beginDate", beginDate), kv("endDate", endDate),
                kv("pageIndex", pageIndex), kv("pageSize", pageSize));

        Page<GetExchangeResponse> response = repository
                .findAllByTransactionDateBetweenOrderByTransactionDate(beginDate, endDate, PageRequest.of(pageIndex,
                        pageSize))
                .map(mapper::mapEntityToResponse);

        log.info("getAllByTransactionDateBetween-end {}", kv("response", response));
        return response;
    }

    private List<ExchangeDetailDto> persistExchangesAndGetExchangeDetails(List<Exchange> exchangeList) {
        return exchangeList.stream()
                .map(exchange -> ExchangeMapper.mapEntityToDto(repository.save(exchange)))
                .collect(Collectors.toList());
    }

    private List<Exchange> getExchangesFromExchangeRates(ExchangeRateResponse exchangeRateResponse, BigDecimal amount,
                                                         Currency base, LocalDate date, LocalDateTime now) {
        return ExchangeRateMapper.crossMapResponse(exchangeRateResponse, amount, base, date, BigDecimal::multiply, now);
    }

}
