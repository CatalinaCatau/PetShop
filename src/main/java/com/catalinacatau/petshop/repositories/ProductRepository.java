package com.catalinacatau.petshop.repositories;

import com.catalinacatau.petshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByCategory(String category);

    List<Product> findProductsByType(String type);

    List<Product> findProductsByCategoryAndType(String category, String type);


}
