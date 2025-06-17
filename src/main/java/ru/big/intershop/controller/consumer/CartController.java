package ru.big.intershop.controller.consumer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.big.intershop.dto.cart.Cart;
import ru.big.intershop.dto.cart.ItemCartShort;
import ru.big.intershop.enums.ViewName;
import ru.big.intershop.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public Mono<Rendering> viewCart() {
        Mono<Cart> cart = cartService.getCart();

        Rendering rendering = Rendering.view(ViewName.CART.getValue())
                .modelAttribute("cart", cart)
                .build();

        return Mono.just(rendering);
    }

    @PostMapping()
    public Mono<Rendering> update(@ModelAttribute ItemCartShort itemCart) {
        return cartService.update(itemCart)
                .then(Mono.just(Rendering.redirectTo("/" + itemCart.from()).build()));
    }
}
