package com.bereda.product_api.service;

import com.bereda.product_api.Repository.ProductRepository;
import com.bereda.product_api.entity.Product;
import com.bereda.product_api.external_api.model.ExternalExchangeRateResponse;
import com.bereda.product_api.external_api.service.ExternalApiService;
import com.bereda.product_api.model.ProductDTO;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.util.List;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ExternalApiService externalApiService;
    private final CSVReaderService csvReaderService;

    public ProductService(final ProductRepository productRepository, final ExternalApiService externalApiService, final CSVReaderService csvReaderService) {
        this.productRepository = productRepository;
        this.externalApiService = externalApiService;
        this.csvReaderService = csvReaderService;
    }

    public void save(final MultipartFile file) {
        final String baseCurrency = "PLN";
        List<ProductDTO> list = csvReaderService.ReaderCSV(file);
        list.forEach(record -> productRepository.save(Product.builder()
                .productName(record.getProductName())
                .description(record.getDescription())
                .originalPrice(record.getPrice())
                .originalCurrency(record.getCurrency())
                .price(record.getPrice() * externalApiService.exchangeRateRequest(record.getCurrency())
                        .flatMap(ExternalExchangeRateResponse::getExchangeRate))
                .currency(baseCurrency)
                .sku(record.getSku())
                .build()));
    }

}

