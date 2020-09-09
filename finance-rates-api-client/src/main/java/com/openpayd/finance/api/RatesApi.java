package com.openpayd.finance.api;

import com.openpayd.finance.api.response.RatesApiResponse;
import feign.Param;
import feign.RequestLine;

import java.util.Currency;
import java.util.List;

public interface RatesApi {

    @RequestLine("GET {path}?base={base}&symbols={symbols}")
    RatesApiResponse getRates(@Param("path") String path, @Param("base") Currency base,
                              @Param("symbols") List<Currency> symbols);

}
