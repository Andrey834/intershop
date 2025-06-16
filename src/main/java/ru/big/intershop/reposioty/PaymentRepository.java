package ru.big.intershop.reposioty;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import ru.big.intershop.model.Payment;

@Repository
public interface PaymentRepository extends R2dbcRepository<Payment, Long> {
}
