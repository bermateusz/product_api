package com.bereda.product_api.controller;

import com.bereda.product_api.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/product")
public class ProductController {

        private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "importCsvFile")
    public void save(@RequestParam("file") MultipartFile file) throws Exception {
        productService.save(file);
    }
}


