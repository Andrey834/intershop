package ru.big.intershop.service;

import org.springframework.data.domain.Pageable;
import ru.big.intershop.dto.PageParam;
import ru.big.intershop.dto.product.ProductShortDto;

import java.util.List;

public interface ProductSearchService {

    List<ProductShortDto> getAll();

    List<ProductShortDto> getAll(PageParam pageParam);

    List<ProductShortDto> getAllByIds(List<Long> ids);

    ProductShortDto getById(Long id);

    int countPages(PageParam pageParam);
}
