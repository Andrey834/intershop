package ru.big.intershop.mapper;

import ru.big.intershop.dto.payment.PaymentDto;
import ru.big.intershop.dto.payment.PaymentResultDto;
import ru.big.intershop.model.Payment;

public class PaymentMapper {
    private PaymentMapper() {
    }

    public static PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .paymentDate(payment.getPaymentDate())
                .sum(payment.getSum())
                .orderId(payment.getOrderId())
                .build();
    }

    public static Payment toModel(PaymentResultDto paymentResultDto) {
        return Payment.builder()
                .sum(paymentResultDto.sum())
                .orderId(paymentResultDto.orderId())
                .paymentDate(paymentResultDto.paidDate())
                .build();
    }
}
