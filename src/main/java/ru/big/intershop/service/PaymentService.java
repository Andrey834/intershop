package ru.big.intershop.service;

import ru.big.intershop.dto.payment.PaymentDto;

import java.util.List;

public interface PaymentService {

    Long payment(Long orderId);

    PaymentDto get(Long paymentId);

    List<PaymentDto> getAllByOrderId(Long orderId);

    void remove(Long paymentId);
}
