package ru.big.intershop.service.impl;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.big.intershop.dto.cart.Cart;
import ru.big.intershop.dto.cart.CartShort;
import ru.big.intershop.dto.cart.ItemCart;
import ru.big.intershop.dto.cart.ItemCartShort;
import ru.big.intershop.service.CartService;
import ru.big.intershop.service.ProductSearchService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final ProductSearchService productSearchService;
    private final ReactiveRedisOperations<String, CartShort> operations;
    private static final String UNKNOWN_USER = "unknown";
    private static final int EXPIRE_CART_DAYS = 7;

    public CartServiceImpl(ProductSearchService productSearchService,
                           ReactiveRedisOperations<String, CartShort> operations) {
        this.productSearchService = productSearchService;
        this.operations = operations;
    }

    @Override
    public Mono<Void> update(ItemCartShort itemCart) {
        if (itemCart.quantity() <= 0) {
            return delete(itemCart.productId());
        }

        return getCartShort()
                .doOnNext(cart -> cart.items().put(itemCart.productId(), itemCart.quantity()))
                .flatMap(cart -> operations.opsForValue().set(UNKNOWN_USER, cart))
                .flatMap(cart -> operations.expire(UNKNOWN_USER, Duration.ofDays(EXPIRE_CART_DAYS)))
                .then(Mono.empty());
    }

    @Override
    public Mono<CartShort> getCartShort() {
        return operations.opsForValue().get(UNKNOWN_USER)
                .switchIfEmpty(Mono.just(new CartShort(new HashMap<>(), LocalDateTime.now())));
    }

    @Override
    public Mono<Void> delete(Long productId) {
        return getCartShort()
                .doOnNext(cart -> cart.items().remove(productId))
                .flatMap(cart -> operations.opsForValue().set(UNKNOWN_USER, cart))
                .then(Mono.empty());
    }

    @Override
    public Mono<Cart> getCart() {
        return getCartShort()
                .map(CartShort::items)
                .flatMapMany(map -> {
                            List<Long> productIds = map.keySet().stream().toList();
                            if (productIds.isEmpty()) {
                                return Mono.empty();
                            }
                            return productSearchService.getAllByIds(productIds)
                                    .map(prod -> new ItemCart(prod, map.get(prod.id())));
                        }
                )
                .collectList()
                .map(Cart::new);
    }

    @Override
    public Mono<ItemCartShort> getItemCart(Long productId) {
        return operations.opsForValue().get(UNKNOWN_USER)
                .map(cart -> cart.items().getOrDefault(productId, 0))
                .map(quantity -> ItemCartShort.builder()
                        .productId(productId)
                        .quantity(quantity)
                        .build()
                );
    }


    @Override
    public Mono<Long> clearCart(Long orderId) {
        return operations.opsForValue().delete(UNKNOWN_USER)
                .then(Mono.just(orderId));
    }
}
