package ru.big.intershop.dto;

public record ItemCartRequest(
        Long productId,
        int quantity
) {
}
