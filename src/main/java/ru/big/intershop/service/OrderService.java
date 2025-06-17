package ru.big.intershop.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.big.intershop.dto.order.OrderDto;
import ru.big.intershop.model.Payment;

public interface OrderService {

    Mono<Long> create();

    Flux<OrderDto> getAll();

    Mono<OrderDto> get(Long orderId);

    Mono<Payment> setPayment(Payment payment);
}
