package ru.big.intershop.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.big.intershop.dto.PageParam;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.mapper.ProductMapper;
import ru.big.intershop.reposioty.ProductRepository;
import ru.big.intershop.service.ProductSearchService;

import java.util.List;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {
    private final ProductRepository productRepository;

    public ProductSearchServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<ProductShortDto> getAll(PageParam pageParam) {
        String search = pageParam.getSearch();

        return productRepository
                .findByTitleContainingOrDescriptionContaining(search, search, pageParam.getPageable())
                .map(ProductMapper::toShortDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<ProductShortDto> getAllByIds(List<Long> ids) {
        return productRepository.getByIdIn(ids)
                .map(ProductMapper::toShortDto);
    }

    @Override
    public Mono<ProductShortDto> getById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found with id: " + id)))
                .map(ProductMapper::toShortDto);
    }

    @Override
    public Mono<PageParam> setMaxPage(PageParam pageParam) {
        String search = pageParam.getSearch();
        return productRepository.countByTitleContainingOrDescriptionContaining(search, search)
                .map(pageParam::setMaxPages);
    }
}
