package ru.big.intershop.reposioty;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.big.intershop.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends R2dbcRepository<Product, Long> {

    Flux<Product> findByTitleContainingOrDescriptionContaining(String title, String description, Pageable pageable);

    Mono<Integer> countByTitleContainingOrDescriptionContaining(String title, String description);

    Flux<Product> getByIdIn(List<Long> ids);
}
