package com.bereda.product_api.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ProductDTO {
    String productName;
    String description;
    Double price;
    String currency;
    String sku;

}
