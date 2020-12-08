package com.bereda.product_api.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder
@Value
public class ProductDTO {
    String productName;
    String description;
    BigDecimal price;
    String currency;
    String sku;

}
