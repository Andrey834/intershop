package ru.big.intershop.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.big.intershop.dto.cart.Cart;
import ru.big.intershop.dto.order.OrderPartDto;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.mapper.OrderPartMapper;
import ru.big.intershop.model.OrderPart;
import ru.big.intershop.reposioty.OrderPartRepository;
import ru.big.intershop.service.CartService;
import ru.big.intershop.service.OrderPartService;
import ru.big.intershop.service.ProductSearchService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderPartServiceImpl implements OrderPartService {
    private final OrderPartRepository orderPartRepository;
    private final ProductSearchService productSearchService;
    private final CartService cartService;

    public OrderPartServiceImpl(OrderPartRepository orderPartRepository,
                                CartService cartService,
                                ProductSearchService productSearchService) {
        this.orderPartRepository = orderPartRepository;
        this.cartService = cartService;
        this.productSearchService = productSearchService;
    }


    @Override
    public Mono<Long> saveParts(Long orderId) {
        Flux<OrderPart> items = cartService.getCart()
                .flatMapIterable(Cart::items)
                .map(item -> OrderPartMapper.itemToOrderPart(orderId, item));

        return orderPartRepository.saveAll(items)
                .then(Mono.just(orderId));
    }

    @Override
    public Mono<Map<Long, List<OrderPartDto>>> getPartsByOrderIds(List<Long> orderIds) {
        Map<Long, List<OrderPartDto>> orderParts = new HashMap<>();
        List<OrderPart> parts = new ArrayList<>();
        Map<Long, ProductShortDto> products = new HashMap<>();
        return orderPartRepository.findByOrderIdIn(orderIds)
                .doOnNext(parts::add)
                .map(OrderPart::getProductId)
                .collectList()
                .flatMapMany(productSearchService::getAllByIds)
                .doOnNext(prod -> products.put(prod.id(), prod))
                .thenMany(Flux.fromIterable(parts))
                .doOnNext(part -> {
                    OrderPartDto orderPartDto = OrderPartMapper.toDto(part, products.get(part.getProductId()));
                    orderParts.putIfAbsent(part.getOrderId(), new ArrayList<>());
                    orderParts.get(part.getOrderId()).add(orderPartDto);
                })
                .then(Mono.just(orderParts));
    }
}
