package com.example.springecommerce.model.dao;

import com.example.springecommerce.model.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDAO extends ListCrudRepository<Product,Long> {

}
