package ru.big.intershop.mapper;

import ru.big.intershop.dto.payment.PaymentDto;
import ru.big.intershop.model.Payment;

public class PaymentMapper {
    private PaymentMapper() {
    }

    public static PaymentDto toDto(Payment payment) {
        if (payment != null) {
            return PaymentDto.builder()
                    .id(payment.getId())
                    .created(payment.getCreated())
                    .isPaid(payment.isPaid())
                    .build();
        } else {
            return null;
        }
    }

    public static Payment toEntity(PaymentDto paymentDto) {
        return Payment.builder()
                .id(paymentDto.id())
                .created(paymentDto.created())
                .paid(paymentDto.isPaid())
                .build();
    }
}
