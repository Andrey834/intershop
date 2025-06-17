package ru.big.intershop.client.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.big.intershop.client.PaymentClient;
import ru.big.intershop.dto.order.OrderDto;
import ru.big.intershop.dto.payment.PaymentResultDto;

import java.time.LocalDateTime;

@Service
public class PaymentClientStubImpl implements PaymentClient {

    @Override
    public Mono<PaymentResultDto> pay(OrderDto order) {
        PaymentResultDto payment = PaymentResultDto.builder()
                .orderId(order.id())
                .sum(order.getTotal())
                .isPaid(true)
                .paidDate(LocalDateTime.now())
                .build();

        if (order.paymentId() == null) {
            return Mono.just(payment);
        }
        return Mono.empty();
    }
}
