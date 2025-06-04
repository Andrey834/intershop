package ru.big.intershop.mapper;

import ru.big.intershop.dto.order.OrderDto;
import ru.big.intershop.dto.order.OrderPartDto;
import ru.big.intershop.model.Order;

import java.math.BigDecimal;
import java.util.List;

public class OrderMapper {
    private OrderMapper() {
    }

    public static OrderDto toDto(Order order) {
        List<OrderPartDto> orderParts = order.getOrderParts().stream()
                .map(OrderPartMapper::toDto)
                .toList();

        BigDecimal total = BigDecimal.ZERO;
        for (OrderPartDto orderPart : orderParts) {
            total = total.add(orderPart.product().price().multiply(new BigDecimal(orderPart.quantity())));
        }
        return OrderDto.builder()
                .id(order.getId())
                .payment(PaymentMapper.toDto(order.getPayment()))
                .created(order.getCreated())
                .parts(orderParts)
                .build();
    }

    public static Order toEntity(OrderDto orderDto) {
        return Order.builder()
                .id(orderDto.id())
                .created(orderDto.created())
                .payment(PaymentMapper.toEntity(orderDto.payment()))
                .build();
    }
}
