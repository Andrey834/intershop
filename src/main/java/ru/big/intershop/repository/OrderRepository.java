package ru.big.intershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.big.intershop.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
