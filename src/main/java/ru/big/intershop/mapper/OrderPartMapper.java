package ru.big.intershop.mapper;

import ru.big.intershop.dto.order.OrderPartDto;
import ru.big.intershop.model.OrderPart;

public class OrderPartMapper {
    private OrderPartMapper() {
    }

    public static OrderPartDto toDto(OrderPart orderPart) {
        return OrderPartDto.builder()
                .id(orderPart.getId())
                .product(ProductMapper.toShortDto(orderPart.getProduct()))
                .quantity(orderPart.getQuantity())
                .price(orderPart.getPrice())
                .build();
    }
}
