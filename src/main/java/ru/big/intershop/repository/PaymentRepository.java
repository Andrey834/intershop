package ru.big.intershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.big.intershop.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
