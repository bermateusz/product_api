package com.bereda.product_api.controller;

import com.bereda.product_api.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final ProductsService productsService;

    public ProductController(final ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping(value = "import-csv-file")
    public void processFile(@RequestParam("file") final MultipartFile file) throws Exception {
        productsService.importProducts(file);
    }
}


