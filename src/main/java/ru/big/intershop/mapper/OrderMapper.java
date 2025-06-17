package ru.big.intershop.mapper;


import ru.big.intershop.dto.order.OrderDto;
import ru.big.intershop.dto.order.OrderPartDto;
import ru.big.intershop.model.Order;

import java.util.List;

public class OrderMapper {

    private OrderMapper() {
    }

    public static OrderDto toDto(Order order, List<OrderPartDto> parts) {
        return OrderDto.builder()
                .id(order.getId())
                .created(order.getCreatedAt())
                .paymentId(order.getPaymentId())
                .parts(parts)
                .build();
    }
}
