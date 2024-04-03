package com.catalinacatau.petshop.controllers;

import com.catalinacatau.petshop.entities.Product;
import com.catalinacatau.petshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    ResponseEntity<?> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/products")
    ResponseEntity<?> getProductByCategoryAndType(@RequestParam(required = false) String category, @RequestParam(required = false) String type) {
        return productService.getProductByCategoryAndType(category, type);
    }

    @GetMapping("/products/search")
    ResponseEntity<?> getProductByCategoryAndType(@RequestParam String name) {
        return productService.getProductByName(name);
    }

    @PostMapping("/products")
    ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {
        return productService.addProduct(product);
    }


}
