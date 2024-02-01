package com.example.springecommerce.api.controller.order;

import com.example.springecommerce.model.Product;
import com.example.springecommerce.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;
    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }
}
