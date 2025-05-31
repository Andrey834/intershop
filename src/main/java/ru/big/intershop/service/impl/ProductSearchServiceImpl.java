package ru.big.intershop.service.impl;

import org.springframework.stereotype.Service;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.mapper.ProductMapper;
import ru.big.intershop.repository.ProductRepository;
import ru.big.intershop.service.ProductSearchService;

import java.util.List;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {
    private final ProductRepository productRepository;

    public ProductSearchServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductShortDto> getAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toShortDto)
                .toList();
    }
}
