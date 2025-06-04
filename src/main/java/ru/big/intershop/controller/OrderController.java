package ru.big.intershop.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.big.intershop.dto.order.OrderDto;
import ru.big.intershop.enums.ViewName;
import ru.big.intershop.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String viewOrders(Model model) {
        List<OrderDto> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping
    public ModelAndView processOrder() {
        Long orderId = orderService.create();
        ModelAndView paymentMav = new ModelAndView("redirect:/" + ViewName.PAYMENT.getValue());
        paymentMav.addObject("orderId", orderId);
        return paymentMav;
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable(name = "id") Long id,
                            Model model) {
        OrderDto order = orderService.getById(id);
        model.addAttribute("order", order);
        return ViewName.ORDER.getValue();
    }
}
