package ru.big.intershop.dto;

import lombok.Builder;
import ru.big.intershop.dto.product.ProductShortDto;

@Builder
public record ItemCart(
        ProductShortDto product,
        int quantity
) {
}
