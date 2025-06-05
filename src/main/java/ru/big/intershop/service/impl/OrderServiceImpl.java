package ru.big.intershop.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.big.intershop.dto.ItemCart;
import ru.big.intershop.dto.order.OrderDto;
import ru.big.intershop.mapper.OrderMapper;
import ru.big.intershop.model.Order;
import ru.big.intershop.model.OrderPart;
import ru.big.intershop.model.Payment;
import ru.big.intershop.model.Product;
import ru.big.intershop.repository.OrderPartRepository;
import ru.big.intershop.repository.OrderRepository;
import ru.big.intershop.service.CartService;
import ru.big.intershop.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderPartRepository orderPartRepository;

    public OrderServiceImpl(CartService cartService,
                            OrderRepository orderRepository,
                            OrderPartRepository orderPartRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderPartRepository = orderPartRepository;
    }

    @Override
    @Transactional
    public Long create() {
        List<ItemCart> cart = cartService.getCart();

        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        Order order = Order.builder()
                .created(LocalDateTime.now())
                .build();

        order = orderRepository.save(order);
        saveParts(order, cart);
        cartService.removeAll();

        return order.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper::toDto)
                .sorted(Comparator.comparing(OrderDto::created))
                .toList()
                .reversed();
    }

    @Override
    public OrderDto getById(Long id) {
        Order order = findById(id);
        return OrderMapper.toDto(order);
    }

    @Override
    public void setPayment(Payment payment) {
        Order order = findById(payment.getOrder().getId());
        order.setPayment(payment);
        orderRepository.save(order);
    }

    private void saveParts(Order order, List<ItemCart> cart) {
        List<OrderPart> orderParts = new ArrayList<>();
        cart.stream().map(item -> OrderPart.builder()
                        .order(order)
                        .product(Product.builder().id(item.product().id()).build())
                        .quantity(item.quantity())
                        .price(item.product().price())
                        .build())
                .forEach(orderParts::add);

        orderPartRepository.saveAll(orderParts);
    }

    private Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }
}
