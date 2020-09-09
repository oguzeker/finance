package com.openpayd.finance;

import com.openpayd.finance.controller.request.ExchangeRateRequest;
import com.openpayd.finance.controller.request.PerformExchangeRequest;
import com.openpayd.finance.controller.response.ExchangeRateResponse;
import com.openpayd.finance.controller.response.PerformExchangeResponse;
import com.openpayd.finance.dto.ExchangeDetailDto;
import com.openpayd.finance.entity.Exchange;
import com.openpayd.finance.mapper.ExchangeMapper;
import com.openpayd.finance.repository.ExchangeRepository;
import com.openpayd.finance.service.impl.ExchangeRateServiceImpl;
import com.openpayd.finance.service.impl.ExchangeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@CoreTest
public class ExchangeServiceTest extends TestBase {

    @InjectMocks
    ExchangeServiceImpl subject;
    @Mock
    ExchangeRateServiceImpl rateService;
    @Mock
    ExchangeRepository repository;
    @Mock
    ExchangeMapper mapper;
    @Captor
    ArgumentCaptor<Exchange> captor;

    @Test
    public void performExchange_Test() {
        PerformExchangeRequest exchangeRequest = createPerformExchangeRequest();
        ExchangeRateRequest rateRequest = createExchangeRateRequest();
        ExchangeRateResponse exchangeRateResponse = createExchangeRateResponse();
        Exchange exchange = createExchange();
        ExchangeDetailDto detailDto = ExchangeMapper.mapEntityToDto(exchange);

        when(mapper.crossMapRequest(exchangeRequest)).thenReturn(rateRequest);
        when(rateService.getExchangeRates(rateRequest)).thenReturn(exchangeRateResponse);
        when(repository.save(any(Exchange.class))).thenReturn(exchange);

        // Subject call
        PerformExchangeResponse performExchangeResponse = subject.performExchange(exchangeRequest);

        verify(repository).save(captor.capture());
        Exchange capturedExchange = captor.getValue();

        assert capturedExchange.getDate() == DATE;
        assert capturedExchange.getAmount().equals(AMOUNT);

        verify(mapper, times(1)).crossMapRequest(any(PerformExchangeRequest.class));
        verify(repository, times(1)).save(any(Exchange.class));
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(repository);

        assert performExchangeResponse.getDate() == DATE;

        assertThat(performExchangeResponse.getDetailsList())
                .hasSize(1)
                .contains(detailDto);
    }

}
