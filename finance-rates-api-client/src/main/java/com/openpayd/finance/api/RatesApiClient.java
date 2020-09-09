package com.openpayd.finance.api;

import com.openpayd.finance.api.request.RatesApiRequest;
import com.openpayd.finance.api.response.RatesApiResponse;
import feign.Feign.Builder;

public class RatesApiClient {

    private final static String API_PATH = "/api/latest";

    private volatile static RatesApiClient instance;
    private final RatesApi ratesApi;

    private RatesApiClient(String ratesApiUrl, Builder builder) {
        ratesApi = builder.target(RatesApi.class, ratesApiUrl);
    }

    public static RatesApiClient getInstance(String ratesApiUrl, Builder builder) {
        synchronized (RatesApiClient.class) {
            if (instance == null) {
                instance = new RatesApiClient(ratesApiUrl, builder);
            }
            return instance;
        }
    }

    public RatesApiResponse getRates(RatesApiRequest request) {
        return ratesApi.getRates(API_PATH, request.getBase(), request.getSymbols());
    }

}
