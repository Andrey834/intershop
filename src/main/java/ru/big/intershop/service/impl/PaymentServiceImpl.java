package ru.big.intershop.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.big.intershop.client.PaymentClient;
import ru.big.intershop.dto.payment.PaymentDto;
import ru.big.intershop.dto.payment.PaymentResultDto;
import ru.big.intershop.mapper.PaymentMapper;
import ru.big.intershop.model.Payment;
import ru.big.intershop.reposioty.PaymentRepository;
import ru.big.intershop.service.OrderService;
import ru.big.intershop.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final PaymentClient paymentClient;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              OrderService orderService,
                              PaymentClient paymentClient) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        this.paymentClient = paymentClient;
    }

    @Override
    public Mono<Long> pay(Long orderId) {
        return orderService.get(orderId)
                .flatMap(paymentClient::pay)
                .filter(PaymentResultDto::isPaid)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Payment is not performed")))
                .map(PaymentMapper::toModel)
                .flatMap(paymentRepository::save)
                .flatMap(orderService::setPayment)
                .map(Payment::getId);
    }

    @Override
    public Mono<PaymentDto> getById(Long id) {
        return findById(id)
                .map(PaymentMapper::toDto);
    }

    private Mono<Payment> findById(Long id) {
        return paymentRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Payment not found with id: " + id)));
    }
}
