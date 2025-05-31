package ru.big.intershop.service;

import ru.big.intershop.dto.product.ProductShortDto;

import java.util.List;

public interface ProductSearchService {

    List<ProductShortDto> getAll();
}
