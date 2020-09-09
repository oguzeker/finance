package com.openpayd.finance;

import com.openpayd.finance.api.RatesApiClient;
import com.openpayd.finance.api.request.RatesApiRequest;
import com.openpayd.finance.api.response.RatesApiResponse;
import com.openpayd.finance.controller.request.ExchangeRateRequest;
import com.openpayd.finance.controller.response.ExchangeRateResponse;
import com.openpayd.finance.mapper.ExchangeRateMapper;
import com.openpayd.finance.service.impl.ExchangeRateServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@CoreTest
public class ExchangeRateServiceTest extends TestBase {

    @InjectMocks
    ExchangeRateServiceImpl subject;
    @Mock
    RatesApiClient client;
    @Mock
    ExchangeRateMapper mapper;
    @Captor
    ArgumentCaptor<ExchangeRateResponse> captor;

    @Test
    public void getExchangeRates_Test() {
        RatesApiRequest apiRequest = createRatesApiRequest();
        ExchangeRateRequest rateRequest = createExchangeRateRequest();
        RatesApiResponse apiResponse = createRatesApiResponse();
        ExchangeRateResponse rateResponse = createExchangeRateResponse();

        when(mapper.mapRequest(rateRequest)).thenReturn(apiRequest);
        when(client.getRates(apiRequest)).thenReturn(apiResponse);
        when(mapper.mapResponse(apiResponse)).thenReturn(rateResponse);

        // Subject call
        ExchangeRateResponse exchangeRatesResponse = subject.getExchangeRates(rateRequest);

        assert exchangeRatesResponse != null;
        assert exchangeRatesResponse.getDate() == DATE;
        assert exchangeRatesResponse.getBase() == USD;

        assertThat(exchangeRatesResponse.getRates())
                .hasSize(1)
                .contains(entry(TRY, TRY_RATE));

        verify(mapper, times(1)).mapRequest(any(ExchangeRateRequest.class));
        verify(mapper, times(1)).mapResponse(any(RatesApiResponse.class));
        verifyNoMoreInteractions(mapper);
    }

}