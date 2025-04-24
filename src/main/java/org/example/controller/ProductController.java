package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.ProductDTO;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "Endpoints para operações com produtos")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    @Operation(summary = "Criar um novo produto")
    public ProductDTO create(@RequestBody ProductDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto por ID")
    public ProductDTO update(@PathVariable UUID id, @RequestBody ProductDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produto por ID")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ProductDTO getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos")
    public List<ProductDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/ean/{ean}")
    @Operation(summary = "Buscar produto por EAN")
    public ProductDTO getByEan(@PathVariable String ean) {
        return service.getByEan(ean);
    }

    @GetMapping("/external/{ean}")
    @Operation(summary = "Buscar dados de produto de fonte externa por EAN")
    public ProductDTO getExternalByEan(@PathVariable String ean) {
        return service.fetchExternalByEan(ean);
    }
}
