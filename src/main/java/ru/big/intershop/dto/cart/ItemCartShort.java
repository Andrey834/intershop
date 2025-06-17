package ru.big.intershop.dto.cart;

import lombok.Builder;

@Builder
public record ItemCartShort(
        Long productId,
        Integer quantity,
        String from
) {
}
