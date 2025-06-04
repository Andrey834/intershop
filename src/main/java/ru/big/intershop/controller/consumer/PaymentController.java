package ru.big.intershop.controller.consumer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.big.intershop.dto.order.OrderDto;
import ru.big.intershop.dto.payment.PaymentDto;
import ru.big.intershop.enums.ViewName;
import ru.big.intershop.exception.PaymentException;
import ru.big.intershop.service.OrderService;
import ru.big.intershop.service.PaymentService;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    private final OrderService orderService;
    private final PaymentService paymentService;

    public PaymentController(OrderService orderService, PaymentService paymentService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @GetMapping()
    public String viewPayment(@RequestParam(name = "orderId") Long orderId, Model model) {
        OrderDto order = orderService.getById(orderId);
        model.addAttribute("order", order);
        return ViewName.PAYMENT.getValue();
    }

    @PostMapping()
    public ModelAndView processPayment(@RequestParam(name = "orderId") Long orderId) {
        Long paymentId = paymentService.payment(orderId);

        ModelAndView paymentResultMav = new ModelAndView(
                "redirect:/" + ViewName.PAYMENT.getValue() + "/" + paymentId);
        paymentResultMav.addObject("paymentId", paymentId);

        return paymentResultMav;
    }

    @GetMapping("/{id}")
    public String viewPaymentResult(@PathVariable("id") Long paymentId, Model model) {
        PaymentDto paymentDto = paymentService.get(paymentId);
        model.addAttribute("payment", paymentDto);
        return ViewName.PAYMENT_RESULT.getValue();
    }

    @ExceptionHandler(PaymentException.class)
    public ModelAndView handleIllegalArgumentException(PaymentException e) {
        return new ModelAndView("redirect:/" + ViewName.ORDER.getValue());
    }
}
