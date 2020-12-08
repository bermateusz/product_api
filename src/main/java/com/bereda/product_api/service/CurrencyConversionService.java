package com.bereda.product_api.service;

import com.bereda.product_api.external_api.model.ExternalExchangeRateResponse;
import com.bereda.product_api.external_api.service.ExternalApiService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyConversionService {

    private final ExternalApiService externalApiService;

    public CurrencyConversionService(final ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    public BigDecimal currencyConversion(final BigDecimal originalPrice, final String fromCurrency, final String toCurrency) {
        return originalPrice.multiply(externalApiService.exchangeRateRequest(fromCurrency, toCurrency)
                .map(ExternalExchangeRateResponse::getValue)
                .orElseThrow());
    }
}
