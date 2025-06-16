package ru.big.intershop.client;


import reactor.core.publisher.Mono;
import ru.big.intershop.dto.order.OrderDto;
import ru.big.intershop.dto.payment.PaymentResultDto;

public interface PaymentClient {

    Mono<PaymentResultDto> pay(OrderDto order);
}
