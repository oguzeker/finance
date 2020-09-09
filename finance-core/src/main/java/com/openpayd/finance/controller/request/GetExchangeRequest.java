package com.openpayd.finance.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetExchangeRequest {

    private String id;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;
    private int pageNumber;
    private int pageSize;

}
