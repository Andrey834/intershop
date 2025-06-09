package ru.big.intershop.service;

import ru.big.intershop.dto.order.OrderDto;
import ru.big.intershop.model.Payment;

import java.util.List;

public interface OrderService {

    Long create();

    List<OrderDto> findAll();

    OrderDto getById(Long id);

    void setPayment(Payment payment);
}
