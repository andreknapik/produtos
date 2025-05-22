package org.example.service;

import org.example.domain.Product;
import org.example.dto.ProductDTO;
import org.example.external.ProductExternalDTO;
import org.example.external.ProductExternalService;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductExternalService externalService;

    private AutoCloseable closeable;

    private ProductDTO sampleDTO;
    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        sampleDTO = new ProductDTO();
        sampleDTO.setId(UUID.randomUUID());
        sampleDTO.setSku("123ABC");
        sampleDTO.setName("Produto Teste");
        sampleDTO.setEan("7891234567890");
        sampleDTO.setQuantity(10);
        sampleDTO.setPrice(new BigDecimal("49.99"));
        sampleDTO.setCategory("Eletrônicos");
        sampleDTO.setImageUrl("http://img.com/img.jpg");

        sampleProduct = new Product();
        sampleProduct.setId(sampleDTO.getId());
        sampleProduct.setSku(sampleDTO.getSku());
        sampleProduct.setName(sampleDTO.getName());
        sampleProduct.setEan(sampleDTO.getEan());
        sampleProduct.setQuantity(sampleDTO.getQuantity());
        sampleProduct.setPrice(sampleDTO.getPrice());
        sampleProduct.setCategory(sampleDTO.getCategory());
        sampleProduct.setImageUrl(sampleDTO.getImageUrl());
    }

    @Test
    void testCreate() {
        when(repository.save(any(Product.class))).thenReturn(sampleProduct);

        ProductDTO created = service.create(sampleDTO);

        assertNotNull(created);
        assertEquals(sampleDTO.getSku(), created.getSku());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void testGetById() {
        when(repository.findById(sampleDTO.getId())).thenReturn(Optional.of(sampleProduct));

        ProductDTO result = service.getById(sampleDTO.getId());

        assertNotNull(result);
        assertEquals(sampleDTO.getId(), result.getId());
        verify(repository).findById(sampleDTO.getId());
    }

    @Test
    void testFetchExternalByEan() {
        ProductExternalDTO.ProductData externalData = new ProductExternalDTO.ProductData();
        externalData.setCode("7891234567890");
        externalData.setProduct_name("Produto Externo");
        externalData.setCategories("Alimentos");
        externalData.setImage_url("http://external.img");

        when(externalService.fetchProductByEan("7891234567890"))
                .thenReturn(Mono.just(externalData));

        ProductDTO dto = service.fetchExternalByEan("7891234567890");

        assertNotNull(dto);
        assertEquals("Produto Externo", dto.getName());
        assertEquals("7891234567890", dto.getEan());
        verify(externalService).fetchProductByEan("7891234567890");
    }

    @Test
    void testFetchExternalByEan_NotFound() {
        when(externalService.fetchProductByEan("0000000000000"))
                .thenReturn(Mono.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                service.fetchExternalByEan("0000000000000"));

        assertEquals("Produto não encontrado na API externa", ex.getMessage());
    }
}