package com.catalinacatau.petshop.repositories;

import com.catalinacatau.petshop.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    public Optional<ShoppingCart> findByUserId(Long userId);
}
