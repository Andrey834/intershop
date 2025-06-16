package ru.big.intershop.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.big.intershop.dto.PageParam;
import ru.big.intershop.dto.product.ProductShortDto;

import java.util.List;

public interface ProductSearchService {

    Flux<ProductShortDto> getAll(PageParam pageParam);

    Flux<ProductShortDto> getAllByIds(List<Long> ids);

    Mono<ProductShortDto> getById(Long id);

    Mono<PageParam> setMaxPage(PageParam pageParam);
}
