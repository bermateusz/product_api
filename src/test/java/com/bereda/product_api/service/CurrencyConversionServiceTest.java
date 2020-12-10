package com.bereda.product_api.service;

import com.bereda.product_api.external_api.model.ExternalExchangeRateResponse;
import com.bereda.product_api.external_api.service.ExternalApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionServiceTest {

    @Mock
    private ExternalApiService externalApiService;

    @InjectMocks
    private CurrencyConversionService currencyConversionService;

    @Test
    void currencyConversionMethod() {
        //given
        when(externalApiService.exchangeRateRequest("PLN", "EUR")).thenReturn(Optional.of(ExternalExchangeRateResponse.builder()
                .value(BigDecimal.valueOf(5.1))
                .build()));

        //when
        currencyConversionService.currencyConversion(BigDecimal.valueOf(50.0), "PLN", "EUR");
        currencyConversionService.currencyConversion(BigDecimal.valueOf(45.0), "PLN", "EUR");
    }

}