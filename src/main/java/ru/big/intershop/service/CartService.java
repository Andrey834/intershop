package ru.big.intershop.service;

import reactor.core.publisher.Mono;
import ru.big.intershop.dto.cart.Cart;
import ru.big.intershop.dto.cart.CartShort;
import ru.big.intershop.dto.cart.ItemCartShort;

public interface CartService {

    Mono<Void> update(ItemCartShort itemCart);

    Mono<CartShort> getCartShort();

    Mono<Void> delete(Long productId);

    Mono<Cart> getCart();

    Mono<ItemCartShort> getItemCart(Long productId);

    Mono<Long> clearCart(Long orderId);
}
