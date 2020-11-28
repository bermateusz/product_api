package com.bereda.product_api.service;

import com.bereda.product_api.Repository.ProductRepository;
import com.bereda.product_api.entity.Product;
import com.bereda.product_api.external_api.model.ExternalExchangeRateResponse;
import com.bereda.product_api.external_api.service.ExternalApiService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository repository;
    private final ExternalApiService externalApiService;

    public ProductService(final ProductRepository repository, final ExternalApiService externalApiService) {
        this.repository = repository;
        this.externalApiService = externalApiService;
    }

    public void save(final MultipartFile file) {
        final String baseCurrency = "PLN";
        try {
            final FileReader fileReader = new FileReader(convertMultiPartToFile(file));
            final CSVReader csvReader = new CSVReaderBuilder(fileReader)
                    .withSkipLines(1)
                    .build();
            final List<String[]> strings = csvReader.readAll();
            strings.forEach(string -> repository.save(Product.builder()
                    .productName(string[0].trim())
                    .description(string[1].trim())
                    .originalPrice(Double.parseDouble(string[2]))
                    .originalCurrency(string[3].trim())
                    .price(Double.parseDouble(string[2]) * externalApiService.exchangeRateRequest(string[3])
                            .flatMap(ExternalExchangeRateResponse::getExchangeRate))
                    .currency(baseCurrency)
                    .sku(string[4].trim())
                    .build()));
        } catch (Exception e) {
            log.error("Exception occurred when reading file.", e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
