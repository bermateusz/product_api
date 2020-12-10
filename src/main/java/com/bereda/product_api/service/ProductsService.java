package com.bereda.product_api.service;

import com.bereda.product_api.Repository.ProductRepository;
import com.bereda.product_api.entity.Product;
import com.bereda.product_api.model.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class ProductsService {

    private static final String BASE_CURRENCY = "PLN";

    private final ProductRepository productRepository;
    private final CurrencyConversionService currencyConversionService;
    private final CSVReaderService csvReaderService;


    public ProductsService(final ProductRepository productRepository, final CurrencyConversionService currencyConversionService,
                           final CSVReaderService csvReaderService) {
        this.productRepository = productRepository;
        this.currencyConversionService = currencyConversionService;
        this.csvReaderService = csvReaderService;
    }

    public void importProducts(final MultipartFile file) {
        final List<ProductDTO> list = csvReaderService.parseFile(file);
        list.forEach(record -> {
            try {
                productRepository.save(Product.builder()
                        .productName(record.getProductName())
                        .description(record.getDescription())
                        .originalPrice(record.getPrice())
                        .originalCurrency(record.getCurrency())
                        .price(currencyConversionService.currencyConversion(record.getPrice(), record.getCurrency(), BASE_CURRENCY))
                        .currency(BASE_CURRENCY)
                        .sku(record.getSku())
                        .build());
            } catch (final Exception e) {
                log.error("An exception occurred when trying to save {}", record, e);
            }
        });
    }
}
//cache @cacheable


