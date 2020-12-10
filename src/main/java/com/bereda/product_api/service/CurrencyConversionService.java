package com.bereda.product_api.service;

import com.bereda.product_api.external_api.model.ExternalExchangeRateResponse;
import com.bereda.product_api.external_api.service.ExternalApiService;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CurrencyConversionService {

    private static final Map<String, Pair> EXCHANGE_RATE_MAP = new HashMap<>();

    private final ExternalApiService externalApiService;

    public CurrencyConversionService(final ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

//    public BigDecimal currencyConversion(final BigDecimal originalPrice, final String fromCurrency, final String toCurrency) {
//        final BigDecimal exchangeRate;
//        final LocalDateTime addedAt;
//
//        if (!EXCHANGE_RATE_MAP.containsKey(fromCurrency + toCurrency)) {
//            exchangeRate = externalApiService.exchangeRateRequest(fromCurrency, toCurrency)
//                    .map(ExternalExchangeRateResponse::getValue)
//                    .orElseThrow();
//            EXCHANGE_RATE_MAP.put(fromCurrency + toCurrency, Pair.builder()
//                    .rateValue(exchangeRate)
//                    .addedAt(LocalDateTime.now())
//                    .build());
//        } else {
//            addedAt = EXCHANGE_RATE_MAP.get(fromCurrency + toCurrency).getAddedAt();
//            if (LocalDateTime.now().isBefore(addedAt.plusMinutes(3))) {
//                log.info("Map contains exchange rate value for key: {}", fromCurrency + toCurrency);
//                exchangeRate = EXCHANGE_RATE_MAP.get(fromCurrency + toCurrency).getRateValue();
//            } else {
//                exchangeRate = externalApiService.exchangeRateRequest(fromCurrency, toCurrency)
//                        .map(ExternalExchangeRateResponse::getValue)
//                        .orElseThrow();
//                EXCHANGE_RATE_MAP.put(fromCurrency + toCurrency, Pair.builder()
//                        .rateValue(exchangeRate)
//                        .addedAt(LocalDateTime.now())
//                        .build());
//            }
//        }
//        return originalPrice.multiply(exchangeRate);
//    }

    public BigDecimal currencyConversion(final BigDecimal originalPrice, final String fromCurrency, final String toCurrency) {
        if (!EXCHANGE_RATE_MAP.containsKey(fromCurrency + toCurrency)) {
            return multiplyingPriceByExchangeRate(originalPrice, fromCurrency, toCurrency);
        } else {
            final LocalDateTime addedAt = EXCHANGE_RATE_MAP.get(fromCurrency + toCurrency).getAddedAt();
            if (LocalDateTime.now().isBefore(addedAt.plusMinutes(3))) {
                log.info("Map contains exchange rate value for key: {}", fromCurrency + toCurrency);
                final BigDecimal exchangeRate = EXCHANGE_RATE_MAP.get(fromCurrency + toCurrency).getRateValue();
                return originalPrice.multiply(exchangeRate);
            } else {
                return multiplyingPriceByExchangeRate(originalPrice, fromCurrency, toCurrency);
            }
        }

    }

    private BigDecimal multiplyingPriceByExchangeRate(final BigDecimal originalPrice, final String fromCurrency, final String toCurrency) {
        final BigDecimal exchangeRate = externalApiService.exchangeRateRequest(fromCurrency, toCurrency)
                .map(ExternalExchangeRateResponse::getValue)
                .orElseThrow();
        EXCHANGE_RATE_MAP.put(fromCurrency + toCurrency, Pair.builder()
                .rateValue(exchangeRate)
                .addedAt(LocalDateTime.now())
                .build());
        return originalPrice.multiply(exchangeRate);
    }
}

@Data
@Builder
class Pair {
    private final LocalDateTime addedAt;
    private final BigDecimal rateValue;
}



