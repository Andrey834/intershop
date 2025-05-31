package ru.big.intershop.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.big.intershop.model.OrderPart;
import ru.big.intershop.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(@Qualifier("cartServiceMemoryImpl") CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String cart() {
        return "cart";
    }

    @PostMapping
    public String add(@RequestParam Long productId, Model model) {
        cartService.add(productId);
        return "redirect:/cart?productId=" + productId;
    }
}
