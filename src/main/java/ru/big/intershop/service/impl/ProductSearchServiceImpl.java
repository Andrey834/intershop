package ru.big.intershop.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.big.intershop.dto.PageParam;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.mapper.ProductMapper;
import ru.big.intershop.model.Product;
import ru.big.intershop.repository.ProductRepository;
import ru.big.intershop.service.ProductSearchService;

import java.util.Comparator;
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
                .sorted(Comparator.comparing(Product::getId))
                .map(ProductMapper::toShortDto)
                .toList();
    }

    @Override
    public List<ProductShortDto> getAll(PageParam pageParam) {
        Sort sort = Sort.by(pageParam.getSortDirection(), pageParam.getSorting().getValue());
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize(), sort);

        return productRepository.findAllByParam(pageParam.getSearch(), pageable).stream()
                .map(ProductMapper::toShortDto)
                .toList();
    }


    @Override
    public List<ProductShortDto> getAllByIds(List<Long> ids) {
        return productRepository.findAllById(ids).stream()
                .map(ProductMapper::toShortDto)
                .toList();
    }

    @Override
    public ProductShortDto getById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toShortDto)
                .orElseThrow(() -> new IllegalArgumentException("Product "));
    }

    @Override
    public int countPages(PageParam pageParam) {
        int count = productRepository.countByParam(pageParam.getSearch());
        return (count % pageParam.getSize() == 0
                ? count / pageParam.getSize() - 1
                : count / pageParam.getSize());
    }
}
