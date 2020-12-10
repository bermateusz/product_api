package com.bereda.product_api.external_api.service;

import com.bereda.product_api.external_api.model.ExternalExchangeRateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ExternalApiService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ExternalApiService(final RestTemplate restTemplate, final @Value("${currency-converter-api.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public Optional<ExternalExchangeRateResponse> exchangeRateRequest(final String fromCurrency, final String toCurrency) {
        log.info("Starting to import exchange rate from: {} to: {} from external API.", fromCurrency, toCurrency);
        final Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("fromCurrency", fromCurrency);
        uriVariables.put("toCurrency", toCurrency);
        try {
            final ResponseEntity<ExternalExchangeRateResponse> exchangeRateResponseEntity =
                    restTemplate.getForEntity(baseUrl,
                            ExternalExchangeRateResponse.class, uriVariables);
            return Optional.ofNullable(exchangeRateResponseEntity.getBody());
        } catch (final Exception e) {
            log.error("An exception occurred when importing exchange rate from: {} to: {}", fromCurrency, toCurrency, e);
            return Optional.empty();
        }
    }
}
