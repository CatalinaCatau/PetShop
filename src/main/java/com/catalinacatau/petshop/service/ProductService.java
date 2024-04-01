package com.catalinacatau.petshop.service;

import com.catalinacatau.petshop.entities.Product;
import com.catalinacatau.petshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    public ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> getProductById(Long id) {
        ResponseEntity<?> response = null;
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isPresent()){
            response = new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    public ResponseEntity<?> addProduct(Product product) {
        ResponseEntity<?> response = null;

        try {
            Product insertedProduct = productRepository.saveAndFlush(product);
            response = new ResponseEntity<>(insertedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
