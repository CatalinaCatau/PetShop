package com.catalinacatau.petshop.configurations;

import com.catalinacatau.petshop.entities.CartItem;
import com.catalinacatau.petshop.entities.Product;
import com.catalinacatau.petshop.entities.ShoppingCart;
import com.catalinacatau.petshop.entities.User;
import com.catalinacatau.petshop.repositories.CartItemRepository;
import com.catalinacatau.petshop.repositories.ProductRepository;
import com.catalinacatau.petshop.repositories.ShoppingCartRepository;
import com.catalinacatau.petshop.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseConfiguration {

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository, UserRepository userRepository, ShoppingCartRepository shoppingCartRepository, CartItemRepository cartItemRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            productRepository.save(new Product(1L, "Hrana uscata pisici", "Pisici", "Hrana", 12.59, 100));
            productRepository.save(new Product(2L, "Jucarie interactiva pentru caini", "Caini", "Jucarie", 25.99, 50));
            productRepository.save(new Product(3L, "Nisip igienic pentru pisici", "Pisici", "Ingrijire", 8.99, 200));
            productRepository.save(new Product(4L, "Hrana uscata pentru caini", "Caini", "Hrana", 15.79, 80));
            productRepository.save(new Product(5L, "Cusca pentru rozatoare", "Rozatoare", "Accesorii", 34.99, 30));
            productRepository.save(new Product(6L, "Acvariu pentru pesti", "Pesti", "Acvariu", 29.99, 20));
            productRepository.save(new Product(7L, "Colier antiparazitar pentru caini", "Caini", "Accesorii", 9.99, 150));
            productRepository.save(new Product(8L, "Perie pentru pisici", "Pisici", "Ingrijire", 7.49, 100));
            productRepository.save(new Product(9L, "Scaun auto pentru caini", "Caini", "Accesorii", 49.99, 10));
            productRepository.save(new Product(10L, "Jucarie din plush pentru Pisici", "Pisici", "Jucarie", 6.29, 120));

            userRepository.save(new User(1L, "admin", passwordEncoder.encode("admin"), "admin admin", "ADMIN"));
            shoppingCartRepository.save(new ShoppingCart(1L, 1L, 0.0D));

            userRepository.save(new User(2L, "user", passwordEncoder.encode("user"), "user user", "USER"));
            shoppingCartRepository.save(new ShoppingCart(2L, 2L, 0.0D));

            cartItemRepository.save(new CartItem(1L, 1L, 1L, 5));
            cartItemRepository.save(new CartItem(2L, 1L, 3L, 3));
            cartItemRepository.save(new CartItem(3L, 1L, 5L, 7));
            cartItemRepository.save(new CartItem(4L, 1L, 7L, 2));
            cartItemRepository.save(new CartItem(5L, 2L, 2L, 4));
            cartItemRepository.save(new CartItem(6L, 2L, 4L, 6));
            cartItemRepository.save(new CartItem(7L, 2L, 6L, 3));
        };
    }
}
