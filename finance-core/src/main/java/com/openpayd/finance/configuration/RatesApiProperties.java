package com.openpayd.finance.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Currency;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "rates-api")
public class RatesApiProperties {

    private String baseUrl;
    private List<Currency> validCurrencyList;

}
