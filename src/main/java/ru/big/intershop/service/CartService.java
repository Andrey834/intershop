package ru.big.intershop.service;

import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.model.OrderPart;

import java.util.List;

public interface CartService {

    void add(Long productId);

    void remove(String sessionId, Long id);

    void removeAll(String sessionId);

    List<ProductShortDto> getAll(String sessionId);
}
