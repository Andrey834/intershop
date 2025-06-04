package ru.big.intershop.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.big.intershop.client.PaymentClient;
import ru.big.intershop.dto.order.OrderDto;

import java.math.BigDecimal;

@Component
@Slf4j
public class PaymentClientStubImpl implements PaymentClient {
    @Override
    public boolean pay(OrderDto orderDto) {
        boolean result = false;
        BigDecimal amount = orderDto.getTotal();

        if (amount.doubleValue() > 0) {
            result = true;
            log.info("Pay amount {}", amount);
        } else {
            log.info("Fail pay amount {}", amount);
        }

        return false;
    }
}
