package ru.big.intershop.dto.payment;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PaymentDto(
        Long id,
        LocalDateTime paymentDate,
        BigDecimal sum,
        Long orderId
) {
}
