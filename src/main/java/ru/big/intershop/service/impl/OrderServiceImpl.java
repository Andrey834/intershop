package ru.big.intershop.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.big.intershop.dto.order.OrderDto;
import ru.big.intershop.mapper.OrderMapper;
import ru.big.intershop.model.Order;
import ru.big.intershop.model.Payment;
import ru.big.intershop.reposioty.OrderRepository;
import ru.big.intershop.service.CartService;
import ru.big.intershop.service.OrderPartService;
import ru.big.intershop.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderPartService orderPartService;
    private final CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderPartService orderPartService,
                            CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderPartService = orderPartService;
        this.cartService = cartService;
    }

    @Override
    public Mono<Long> create() {
        Order order = Order.builder()
                .createdAt(LocalDateTime.now())
                .build();

        return orderRepository.save(order)
                .map(Order::getId)
                .flatMap(orderPartService::saveParts)
                .flatMap(cartService::clearCart);
    }

    @Override
    public Flux<OrderDto> getAll() {
        Map<Long, Order> orders = new HashMap<>();
        List<OrderDto> orderDtos = new ArrayList<>();

        return orderRepository.findAll()
                .doOnNext(order -> orders.put(order.getId(), order))
                .map(Order::getId)
                .collectList()
                .flatMap(orderPartService::getPartsByOrderIds)
                .doOnNext(mapParts ->
                        orders.keySet().forEach(orderId -> {
                            OrderDto orderDto = OrderMapper.toDto(orders.get(orderId), mapParts.get(orderId));
                            orderDtos.add(orderDto);
                        }))
                .thenMany(Flux.fromIterable(orderDtos))
                .sort(Comparator.comparing(OrderDto::created).reversed());
    }

    @Override
    public Mono<OrderDto> get(Long orderId) {
        Mono<Order> orderMono = findById(orderId);
        return orderPartService.getPartsByOrderIds(List.of(orderId))
                .map(mapParts -> mapParts.get(orderId))
                .zipWith(orderMono,
                        (list, order) -> OrderMapper.toDto(order, list)
                );
    }

    @Override
    public Mono<Payment> setPayment(Payment payment) {
        return findById(payment.getOrderId())
                .doOnNext(order -> order.setPaymentId(payment.getId()))
                .flatMap(orderRepository::save)
                .then(Mono.just(payment));
    }

    private Mono<Order> findById(Long id) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Order not found with id: " + id)));
    }
}
