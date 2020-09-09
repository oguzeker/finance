package com.openpayd.finance.mapper;

import com.openpayd.finance.controller.request.ExchangeRateRequest;
import com.openpayd.finance.controller.request.PerformExchangeRequest;
import com.openpayd.finance.controller.response.GetExchangeResponse;
import com.openpayd.finance.dto.ExchangeDetailDto;
import com.openpayd.finance.entity.Exchange;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExchangeMapper {

    Exchange mapRequest(PerformExchangeRequest request);

    static ExchangeDetailDto mapEntityToDto(Exchange entity) {
        return ExchangeDetailDto.builder()
                .calculated(entity.getCalculated())
                .rate(entity.getRate())
                .symbol(entity.getSymbol())
                .transactionId(entity.getId())
                .build();
    }

    GetExchangeResponse mapEntityToResponse(Exchange entity);

    ExchangeRateRequest crossMapRequest(PerformExchangeRequest request);

}
