package ru.big.intershop.client;

import ru.big.intershop.dto.order.OrderDto;

import java.math.BigDecimal;

public interface PaymentClient {
    boolean pay(OrderDto orderDto);
}
