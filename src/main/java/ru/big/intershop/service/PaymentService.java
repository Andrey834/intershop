package ru.big.intershop.service;

import reactor.core.publisher.Mono;
import ru.big.intershop.dto.payment.PaymentDto;

public interface PaymentService {

    Mono<Long> pay(Long orderId);

    Mono<PaymentDto> getById(Long id);
}
