package ru.big.intershop.controller.consumer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.big.intershop.dto.order.OrderDto;
import ru.big.intershop.enums.ViewName;
import ru.big.intershop.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Mono<Rendering> viewOrders() {
        Flux<OrderDto> orders = orderService.getAll();

        Rendering rendering = Rendering.view(ViewName.ORDERS.getValue())
                .modelAttribute("orders", orders)
                .build();

        return Mono.just(rendering);
    }

    @GetMapping("/{id}")
    public Mono<Rendering> viewOrder(@PathVariable(name = "id") Long id) {
        Mono<OrderDto> order = orderService.get(id);

        Rendering rendering = Rendering.view(ViewName.ORDER.getValue())
                .modelAttribute("order", order)
                .build();

        return Mono.just(rendering);
    }

    @PostMapping
    public Mono<Rendering> processOrder() {
        return orderService.create().flatMap(id -> {
            Rendering rendering = Rendering.redirectTo("/order/" + id)
                    .build();

            return Mono.just(rendering);
        });
    }
}
