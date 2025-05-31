package ru.big.intershop.dto.product;

import java.math.BigDecimal;

public record ProductRequest(
        String title,
        String description,
        BigDecimal price
) {
}
