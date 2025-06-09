package ru.big.intershop.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.big.intershop.client.PaymentClient;
import ru.big.intershop.dto.order.OrderDto;
import ru.big.intershop.dto.payment.PaymentDto;
import ru.big.intershop.exception.PaymentException;
import ru.big.intershop.mapper.PaymentMapper;
import ru.big.intershop.model.Order;
import ru.big.intershop.model.Payment;
import ru.big.intershop.repository.PaymentRepository;
import ru.big.intershop.service.OrderService;
import ru.big.intershop.service.PaymentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentClient paymentClient;
    private final OrderService orderService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentClient paymentClient, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.paymentClient = paymentClient;
        this.orderService = orderService;
    }

    @Override
    @Transactional
    public Long payment(Long orderId) {
        OrderDto orderDto = orderService.getById(orderId);
        Payment payment = Payment.builder()
                .order(Order.builder().id(orderDto.id()).build())
                .created(LocalDateTime.now())
                .paid(false)
                .build();

        Payment newPayment = paymentRepository.save(payment);

        boolean isSuccessPayment = paymentClient.pay(orderDto);
        if (isSuccessPayment) {
            newPayment.setPaid(true);
            newPayment = paymentRepository.save(newPayment);
            orderService.setPayment(newPayment);
            return newPayment.getId();
        } else {
            paymentRepository.delete(newPayment);
            throw new PaymentException("Order is not paid");
        }
    }

    @Override
    public PaymentDto get(Long paymentId) {
        return PaymentMapper.toDto(getPayment(paymentId));
    }

    @Override
    public List<PaymentDto> getAllByOrderId(Long orderId) {
        return paymentRepository.findAllByOrderId(orderId).stream()
                .map(PaymentMapper::toDto)
                .toList();
    }

    @Override
    public void remove(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    private Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
    }


}
