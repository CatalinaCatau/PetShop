package com.catalinacatau.petshop.repositories;

import com.catalinacatau.petshop.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    public List<CartItem> findByShoppingCartId(Long shoppingCartId);

    public Optional<CartItem> findByShoppingCartIdAndProductId(Long shoppingCartId, Long productId);

    public void deleteAllByShoppingCartId(Long shoppingCartId);
}
