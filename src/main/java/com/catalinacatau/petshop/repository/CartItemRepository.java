package com.catalinacatau.petshop.repository;

import com.catalinacatau.petshop.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    public List<CartItem> findByShoppingCartId(Long id);
}
