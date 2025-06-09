package ru.big.intershop.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.big.intershop.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
            SELECT p FROM Product p
            WHERE :search = ''
            OR lower(p.title) LIKE lower(concat('%',:search,'%'))
            OR lower(p.description) LIKE lower(concat('%',:search,'%'))
            """)
    List<Product> findAllByParam(@Param("search") String search, Pageable pageable);

    @Query(value = """
            SELECT count(*) FROM Product p
            WHERE :search = ''
            OR lower(p.title) LIKE lower(concat('%',:search,'%'))
            OR lower(p.description) LIKE lower(concat('%',:search,'%'))
            """)
    int countByParam(@Param("search") String search);
}
