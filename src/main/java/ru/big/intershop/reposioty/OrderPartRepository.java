package ru.big.intershop.reposioty;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.big.intershop.model.OrderPart;

import java.util.List;

@Repository
public interface OrderPartRepository extends R2dbcRepository<OrderPart, Long> {

    Flux<OrderPart> findByOrderId(Long orderId);

    Flux<OrderPart> findByOrderIdIn(List<Long> ids);

}
