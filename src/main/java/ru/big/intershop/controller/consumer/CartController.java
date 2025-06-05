package ru.big.intershop.controller.consumer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.big.intershop.dto.ItemCart;
import ru.big.intershop.dto.ItemCartRequest;
import ru.big.intershop.enums.ViewName;
import ru.big.intershop.service.CartService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String cart(Model model) {
        List<ItemCart> cart = cartService.getCart();
        BigDecimal total = cartService.getTotal();
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return ViewName.CART.getValue();
    }

    @PostMapping
    public String add(@ModelAttribute ItemCartRequest itemCartRequest, @RequestParam String from) {
        cartService.update(itemCartRequest);
        return "redirect:/" + from;
    }

    @PostMapping("/delete")
    public String deletePosition(@RequestParam(name = "productId") Long productId) {
        cartService.remove(productId);
        return "redirect:/" + ViewName.CART.getValue();
    }
}
