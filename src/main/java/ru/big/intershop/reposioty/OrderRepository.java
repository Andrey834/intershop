package ru.big.intershop.reposioty;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import ru.big.intershop.model.Order;

@Repository
public interface OrderRepository extends R2dbcRepository<Order, Long> {
}
