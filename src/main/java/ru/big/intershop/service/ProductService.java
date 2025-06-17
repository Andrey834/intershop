package ru.big.intershop.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.big.intershop.dto.product.ProductDto;
import ru.big.intershop.dto.product.ProductRequest;

public interface ProductService {

    Mono<Long> save(ProductRequest request);

    Mono<Long> update(Long id, ProductRequest request);

    Mono<Void> delete(Long id);

    Flux<ProductDto> getAll();
}
