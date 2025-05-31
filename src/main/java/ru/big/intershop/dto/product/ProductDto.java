package ru.big.intershop.dto.product;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDto(
        Long id,
        String title,
        String description,
        BigDecimal price,
        String largeImageUrl,
        String created
) {
}
