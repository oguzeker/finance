package com.openpayd.finance.controller;

import com.openpayd.finance.controller.response.ExchangeRateResponse;
import com.openpayd.finance.service.impl.ExchangeRateServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("exchange-rate")
public class ExchangeRateController {

    private final ExchangeRateServiceImpl service;

    @GetMapping
    public ResponseEntity<ExchangeRateResponse> getExchangeRate(@RequestParam Currency base,
                                                                @RequestParam List<Currency> symbols) {
        return ResponseEntity.ok(service.getExchangeRates(base, symbols));
    }

}
