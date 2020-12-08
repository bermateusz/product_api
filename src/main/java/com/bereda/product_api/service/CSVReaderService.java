package com.bereda.product_api.service;

import com.bereda.product_api.model.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CSVReaderService {

    public List<ProductDTO> parseFile(final MultipartFile file) {
        List<ProductDTO> productDTOArrayList = new ArrayList<>();
        try {
            final InputStreamReader streamReader = new InputStreamReader(file.getInputStream());
            final CSVParser csvParser = new CSVParser(streamReader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
            csvParser.forEach(record -> productDTOArrayList.add(ProductDTO.builder()
                    .productName(record.get("product_name"))
                    .description(record.get("description"))
                    .price(new BigDecimal(record.get("price")))
                    .currency(record.get("currency"))
                    .sku(record.get("sku"))
                    .build()));
        } catch (Exception e) {
            log.error("Exception occurred when reading file.", e);
        }
        return productDTOArrayList;
    }
}
