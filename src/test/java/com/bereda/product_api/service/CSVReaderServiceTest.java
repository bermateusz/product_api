package com.bereda.product_api.service;

import com.bereda.product_api.model.ProductDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CSVReaderServiceTest {

    @InjectMocks
    private CSVReaderService csvReaderService;

    @Test
    void shouldParseFile() throws IOException {
        //given
        final Resource resource = new ClassPathResource("scratch.csv");
        final MultipartFile file = new MockMultipartFile("scratch.csv", resource.getInputStream());

        //when
        List<ProductDTO> list = csvReaderService.parseFile(file);

        //then
        assertThat(list).containsOnly(
                ProductDTO.builder()
                        .productName("Fartuch")
                        .description("niebieski fartuszek")
                        .price(BigDecimal.valueOf(10))
                        .currency("PLN")
                        .sku("00-111-222")
                        .build(),
                ProductDTO.builder()
                        .productName("Kawa")
                        .description("Tchibo")
                        .price(BigDecimal.valueOf(20))
                        .currency("EUR")
                        .sku("111-251-222")
                        .build(),
                ProductDTO.builder()
                        .productName("Klawiatura")
                        .description("zielona")
                        .price(BigDecimal.valueOf(30))
                        .currency("USD")
                        .sku("1234-zzasd")
                        .build(),
                ProductDTO.builder()
                        .productName("Produkt bez opisu")
                        .description("")
                        .price(BigDecimal.valueOf(50))
                        .currency("USD")
                        .sku("222-aaaa-zzzz")
                        .build());

    }


}