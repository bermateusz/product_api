package com.bereda.product_api.Repository;

import com.bereda.product_api.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
