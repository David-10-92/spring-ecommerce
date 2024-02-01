package com.example.springecommerce.service;

import com.example.springecommerce.model.LocalUser;
import com.example.springecommerce.model.Product;
import com.example.springecommerce.model.dao.ProductDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductDAO productDAO;


    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getProducts(){
        return productDAO.findAll() ;
    }
}
