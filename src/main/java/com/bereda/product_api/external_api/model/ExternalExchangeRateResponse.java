package com.bereda.product_api.external_api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder
@Value
@JsonDeserialize(builder = ExternalExchangeRateResponse.ExternalExchangeRateResponseBuilder.class)
public class ExternalExchangeRateResponse {
    BigDecimal value;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class ExternalExchangeRateResponseBuilder {
    }
}