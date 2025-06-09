package ru.big.intershop.service;

import ru.big.intershop.dto.ItemCart;
import ru.big.intershop.dto.ItemCartRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CartService {

    void update(ItemCartRequest itemCartRequest);

    void remove(Long productId);

    void removeAll();

    Map<Long, Integer> getAll();

    ItemCart get(Long productId);

    List<ItemCart> getCart();

    BigDecimal getTotal();
}
