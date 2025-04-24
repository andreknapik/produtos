package org.example.service;

import org.example.domain.Product;
import org.example.dto.ProductDTO;
import org.example.external.ProductExternalDTO;
import org.example.external.ProductExternalService;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductExternalService externalService;

    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setName(product.getName());
        dto.setEan(product.getEan());
        dto.setQuantity(product.getQuantity());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setImageUrl(product.getImageUrl());
        return dto;
    }

    private Product fromDTO(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setSku(dto.getSku());
        product.setName(dto.getName());
        product.setEan(dto.getEan());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        return product;
    }

    @Override
    public ProductDTO create(ProductDTO dto) {
        return toDTO(repository.save(fromDTO(dto)));
    }

    @Override
    public ProductDTO update(UUID id, ProductDTO dto) {
        Product product = repository.findById(id).orElseThrow();
        product.setName(dto.getName());
        product.setEan(dto.getEan());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        return toDTO(repository.save(product));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public ProductDTO getById(UUID id) {
        return toDTO(repository.findById(id).orElseThrow());
    }

    @Override
    public List<ProductDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getByEan(String ean) {
        return toDTO(repository.findByEan(ean).orElseThrow());
    }

    @Override
    public ProductDTO fetchExternalByEan(String ean) {
        ProductExternalDTO.ProductData externalData = externalService.fetchProductByEan(ean).block();

        if (externalData == null || externalData.getProduct_name() == null) {
            throw new RuntimeException("Produto não encontrado na API externa");
        }

        ProductDTO dto = new ProductDTO();
        dto.setEan(externalData.getCode());
        dto.setName(externalData.getProduct_name());
        dto.setCategory(externalData.getCategories());
        dto.setImageUrl(externalData.getImage_url());
        return dto;
    }
}