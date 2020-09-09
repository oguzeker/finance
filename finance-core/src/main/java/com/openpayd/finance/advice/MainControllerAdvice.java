package com.openpayd.finance.advice;

import com.openpayd.finance.configuration.RatesApiProperties;
import com.openpayd.finance.exception.FinanceCoreError;
import com.openpayd.finance.exception.FinanceCoreException;
import com.openpayd.finance.util.MessageSourceUtil;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Currency;
import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class MainControllerAdvice {

    private final RatesApiProperties ratesApiProperties;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<FinanceCoreException> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        MethodParameter parameter = ex.getParameter();
        String parameterType = parameter.getParameterType().getName();
        String parameterValue = String.valueOf(ex.getValue());
        String parameterName = ex.getName();

        List<Currency> validCurrencyList = ratesApiProperties.getValidCurrencyList();

        FinanceCoreException exception;
        if (parameterType.equals(Currency.class.getName()) && !isValidCurrency(validCurrencyList, parameterValue)) {
            exception = new FinanceCoreException(FinanceCoreError.INVALID_CURRENCY, ex.getRootCause(), parameterValue,
                    validCurrencyList);
        } else {
            exception = new FinanceCoreException(FinanceCoreError.METHOD_ARGUMENT_TYPE_MISMATCH, ex.getRootCause(),
                    parameterType, parameterName);
        }

        return prepareResponse(exception);
    }

    @ExceptionHandler(FeignException.class)
    public final ResponseEntity<FinanceCoreException> handleFeignException(FeignException ex,
                                                                           WebRequest request) {
        return prepareResponse(new FinanceCoreException(FinanceCoreError.API_CONNECTION_FAILURE, ex,
                ex.request().url()));
    }

    private boolean isValidCurrency(List<Currency> validCurrencyList, String parameterValue) {
        return validCurrencyList.stream()
                .anyMatch(currency -> currency.getCurrencyCode().equals(parameterValue));
    }

    private static ResponseEntity<FinanceCoreException> prepareResponse(FinanceCoreException exception) {
        FinanceCoreException ex = MessageSourceUtil.prepareException(exception);
        log.error("Controller-exception {}", kv("exception", ex));
        return ResponseEntity.status(exception.getError().getHttpStatus()).body(ex);
    }

}
