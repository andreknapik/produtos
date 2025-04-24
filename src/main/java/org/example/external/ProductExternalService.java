package org.example.external;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductExternalService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://world.openfoodfacts.org/api/v0/product")
            .build();

    public Mono<ProductExternalDTO.ProductData> fetchProductByEan(String ean) {
        return webClient.get()
                .uri("/{ean}.json", ean)
                .retrieve()
                .bodyToMono(ProductExternalDTO.class)
                .map(ProductExternalDTO::getProduct);
    }
}