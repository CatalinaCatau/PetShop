package com.catalinacatau.petshop.services;

import com.catalinacatau.petshop.entities.Product;
import com.catalinacatau.petshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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

        if (optionalProduct.isPresent()) {
            response = new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    public ResponseEntity<?> getProductByCategoryAndType(String category, String type) {
        ResponseEntity<?> response = null;
        List<Product> productList;

        if (category != null && type != null) {
            productList = productRepository.findProductsByCategoryAndType(category, type);
        } else if (category != null) {
            productList = productRepository.findProductsByCategory(category);
        } else if (type != null) {
            productList = productRepository.findProductsByType(type);
        } else {
            productList = productRepository.findAll();
        }

        if (productList.isEmpty()) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(productList, HttpStatus.OK);
        }

        return response;
    }

    public ResponseEntity<?> getProductByName(String name) {
        ResponseEntity<?> response = null;
        String[] keywords = name.split("[,\\s]+");

        List<Product> productList = productRepository.findAll();

        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getName() == null) {
                productList.remove(i);
                i--;
            } else {
                for (String keyword : keywords) {
                    if (!productList.get(i).getName().toLowerCase().contains(keyword.toLowerCase())) {
                        productList.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }

        if (productList.isEmpty()) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(productList, HttpStatus.OK);
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
