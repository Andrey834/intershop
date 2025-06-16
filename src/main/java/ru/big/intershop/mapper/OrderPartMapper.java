package ru.big.intershop.mapper;


import ru.big.intershop.dto.cart.ItemCart;
import ru.big.intershop.dto.order.OrderPartDto;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.model.OrderPart;

public class OrderPartMapper {
    private OrderPartMapper() {
    }

    public static OrderPartDto toDto(OrderPart orderPart, ProductShortDto product) {
        return OrderPartDto.builder()
                .id(orderPart.getId())
                .price(orderPart.getPrice())
                .quantity(orderPart.getQuantity())
                .product(product)
                .build();

    }

    public static OrderPart itemToOrderPart(Long orderId, ItemCart itemCart) {
        return OrderPart.builder()
                .orderId(orderId)
                .productId(itemCart.product().id())
                .price(itemCart.product().price())
                .quantity(itemCart.quantity())
                .build();
    }
}
