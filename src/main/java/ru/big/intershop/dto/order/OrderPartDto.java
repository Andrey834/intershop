package ru.big.intershop.dto.order;

import lombok.Builder;
import ru.big.intershop.dto.product.ProductShortDto;

import java.math.BigDecimal;

@Builder
public record OrderPartDto(
        Long id,
        ProductShortDto product,
        BigDecimal price,
        int quantity
) {
}
