package com.bereda.product_api.service;

import com.bereda.product_api.Repository.ProductRepository;
import com.bereda.product_api.entity.Product;
import com.bereda.product_api.model.ProductDTO;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository repository;
    private final List<ProductDTO> productDTOList;

    public ProductService(ProductRepository repository, List<ProductDTO> productDTOList) {
        this.repository = repository;
        this.productDTOList = productDTOList;
    }



    public void save(final MultipartFile file) {
        try {
            final FileReader fileReader = new FileReader(convertMultiPartToFile(file));
            final CSVReader csvReader = new CSVReaderBuilder(fileReader)
                    .withSkipLines(1)
                    .build();
            final List<String[]> strings = csvReader.readAll();
            for (String[] string : strings) {
               Product product = Product.builder()
                        .productName(string[0])
                        .description(string[1])
                        .price(Double.parseDouble(string[2]))
                        .currency(string[3])
                        .sku(string[4])
                        .build();
               repository.save(product);
            }
        } catch (Exception e) {
            log.error("Exception occurred when reading file.", e);
        }
    }

    public List<String[]> readCsvFile(final MultipartFile file) {
        try {
            final FileReader fileReader = new FileReader(convertMultiPartToFile(file));
            final CSVReader csvReader = new CSVReaderBuilder(fileReader)
                    .withSkipLines(1)
                    .build();
            final List<String[]> strings = csvReader.readAll();
            for (String[] string : strings) {
                log.info(Arrays.toString(string));
            }
            return strings;
        } catch (Exception e) {
            log.error("Exception occurred when reading file.", e);
            return null;
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
