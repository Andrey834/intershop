package ru.big.intershop.service;

import ru.big.intershop.dto.product.ProductDto;
import ru.big.intershop.dto.product.ProductRequest;
import ru.big.intershop.dto.product.ProductUpdate;

public interface ProductService {

    ProductDto create(ProductRequest productRequest);

    void update(Long id, ProductRequest productRequest);

    ProductDto get(Long id);

    void delete(Long id);

    boolean existById(Long id);
}
