package com.bereda.product_api.service;

import com.bereda.product_api.model.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CSVReaderService {

    public List<ProductDTO> ReaderCSV(final MultipartFile file) {
        List<ProductDTO>list = new ArrayList<>();
        try {
            final FileReader fileReader = new FileReader(convertMultiPartToFile(file));
            final CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
            csvParser.forEach(record -> list.add(ProductDTO.builder()
                    .productName(record.get("product_name"))
            .description(record.get("description"))
            .price(Double.parseDouble(record.get("price")))
            .currency(record.get("currency"))
            .sku(record.get("sku"))
            .build()));
        } catch (Exception e) {
            log.error("Exception occurred when reading file.", e);
        }
        return list;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
