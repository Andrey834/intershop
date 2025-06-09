package ru.big.intershop.dto.payment;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PaymentDto(
        Long id,
        LocalDateTime created,
        boolean isPaid
) {
}
