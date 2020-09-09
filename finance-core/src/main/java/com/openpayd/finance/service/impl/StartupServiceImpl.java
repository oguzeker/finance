package com.openpayd.finance.service.impl;

import com.openpayd.finance.configuration.RatesApiProperties;
import com.openpayd.finance.entity.Exchange;
import com.openpayd.finance.repository.ExchangeRepository;
import com.openpayd.finance.service.StartupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@AllArgsConstructor
public class StartupServiceImpl implements StartupService {

    private static final int NUMBER_FIFTY = 50;
    private static final int NUMBER_ZERO = 0;
    private static final int NUMBER_FIVE = 5;
    private static final int SCALE = 9;

    private final ExchangeRepository repository;
    private final RatesApiProperties ratesApiProperties;

    @PostConstruct
    public void initialize() {
        log.info("init-begin");
        Instant now = Instant.now();
        Instant before = now.minus(Duration.ofDays(NUMBER_FIVE));

        List<Currency> currencyList = ratesApiProperties.getValidCurrencyList();
        int listSize = currencyList.size();

        int count = NUMBER_ZERO;
        while (count < NUMBER_FIFTY) {
            BigDecimal amount = new BigDecimal(getRandomInteger(NUMBER_FIVE));
            BigDecimal rate = getRandomBigDecimal(listSize);
            repository.save(Exchange.builder()
                    .base(currencyList.get(getRandomInteger(listSize)))
                    .symbol(currencyList.get(getRandomInteger(listSize)))
                    .date(getDateBetween(before, now))
                    .amount(amount)
                    .rate(rate)
                    .calculated(amount.multiply(rate).setScale(SCALE, RoundingMode.DOWN))
                    .transactionDate(getDateTimeBetween(before, now))
                    .build());

            count++;
        }

        log.info("init-end {}", kv("count", count));
    }

    private int getRandomInteger(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    private long getRandomLong(long origin, long bound) {
        return ThreadLocalRandom.current().nextLong(origin, bound);
    }

    private BigDecimal getRandomBigDecimal(int bound) {
        BigDecimal max = new BigDecimal(bound);
        BigDecimal randomFromDouble = BigDecimal.valueOf(Math.random());

        return randomFromDouble.divide(max, SCALE, RoundingMode.DOWN);
    }

    private LocalDate getDateBetween(Instant originInclusive, Instant boundExclusive) {
        long random = getRandomLong(originInclusive.toEpochMilli(), boundExclusive.toEpochMilli());

        return LocalDate.ofInstant(Instant.ofEpochMilli(random), ZoneId.systemDefault());
    }

    private LocalDateTime getDateTimeBetween(Instant originInclusive, Instant boundExclusive) {
        long random = getRandomLong(originInclusive.toEpochMilli(), boundExclusive.toEpochMilli());

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(random), ZoneId.systemDefault());
    }

}