package ru.big.intershop.controller.consumer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.big.intershop.dto.order.OrderDto;
import ru.big.intershop.dto.payment.PaymentDto;
import ru.big.intershop.enums.ViewName;
import ru.big.intershop.service.OrderService;
import ru.big.intershop.service.PaymentService;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderService orderService;

    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @GetMapping("/order/{orderId}")
    public Mono<Rendering> viewPayment(@PathVariable Long orderId) {
        Mono<OrderDto> order = orderService.get(orderId);

        Rendering rendering = Rendering.view(ViewName.PAYMENT.getValue())
                .modelAttribute("order", order)
                .build();

        return Mono.just(rendering);
    }

    @PostMapping("/order/{orderId}")
    public Mono<Rendering> processPayment(@PathVariable Long orderId) {
        return paymentService.pay(orderId)
                .map(id -> Rendering.redirectTo("/payment/" + id).build());
    }

    @GetMapping("/{id}")
    public Mono<Rendering> viewPaymentResult(@PathVariable("id") Long paymentId) {
        Mono<PaymentDto> payment = paymentService.getById(paymentId);

        Rendering rendering = Rendering.view(ViewName.PAYMENT_RESULT.getValue())
                .modelAttribute("payment", payment)
                .build();

        return Mono.just(rendering);
    }
}
