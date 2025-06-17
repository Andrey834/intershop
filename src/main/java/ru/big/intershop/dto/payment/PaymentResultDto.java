package ru.big.intershop.dto.payment;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PaymentResultDto(
        Long orderId,
        BigDecimal sum,
        LocalDateTime paidDate,
        boolean isPaid
) {

}
