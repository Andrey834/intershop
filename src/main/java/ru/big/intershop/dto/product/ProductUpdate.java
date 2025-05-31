package ru.big.intershop.dto.product;

import java.math.BigDecimal;

public record ProductUpdate(
        String title,
        String description,
        BigDecimal price
) {
}
