package org.example.service;

import org.example.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDTO create(ProductDTO dto);
    ProductDTO update(UUID id, ProductDTO dto);
    void delete(UUID id);
    ProductDTO getById(UUID id);
    List<ProductDTO> getAll();
    ProductDTO getByEan(String ean);
    ProductDTO fetchExternalByEan(String ean);
}