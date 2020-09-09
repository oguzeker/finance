package com.openpayd.finance.controller;

import com.openpayd.finance.controller.request.PerformExchangeRequest;
import com.openpayd.finance.controller.response.GetExchangeResponse;
import com.openpayd.finance.controller.response.PerformExchangeResponse;
import com.openpayd.finance.service.impl.ExchangeServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("exchange")
public class ExchangeController {

    private final ExchangeServiceImpl service;

    @PostMapping
    public ResponseEntity<PerformExchangeResponse> performExchange(@RequestBody PerformExchangeRequest request) {
        return ResponseEntity.ok(service.performExchange(request));
    }

    @GetMapping
    public ResponseEntity<Page<GetExchangeResponse>> getAllExchanges(@RequestParam int pageIndex,
                                                                     @RequestParam int pageSize) {
        return ResponseEntity.ok(service.getAll(pageIndex, pageSize));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetExchangeResponse> getExchangeById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("begin-date/{begin-date}/end-date/{end-date}")
    public ResponseEntity<Page<GetExchangeResponse>> getExchangesBetweenDates(
            @PathVariable("begin-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginDate,
            @PathVariable("end-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam int pageIndex, @RequestParam int pageSize) {
        return ResponseEntity.ok(service.getAllByTransactionDateBetween(beginDate, endDate, pageIndex, pageSize));
    }

}
