package ru.big.intershop.dto;

import ru.big.intershop.dto.product.ProductShortDto;

public record ItemCart(
        ProductShortDto product,
        int quantity
) {
}
