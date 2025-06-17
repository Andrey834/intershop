package ru.big.intershop.service;

import reactor.core.publisher.Mono;
import ru.big.intershop.dto.order.OrderPartDto;

import java.util.List;
import java.util.Map;

public interface OrderPartService {

    Mono<Long> saveParts(Long orderId);

    Mono<Map<Long, List<OrderPartDto>>>  getPartsByOrderIds(List<Long> orderIds);
}
