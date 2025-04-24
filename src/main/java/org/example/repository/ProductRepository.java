package org.example.repository;

import org.example.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByEan(String ean);
    Optional<Product> findBySku(String sku);
}