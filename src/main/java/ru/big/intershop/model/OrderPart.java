package ru.big.intershop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_part")
public class OrderPart {
    @Id
    private Long id;
    private Long orderId;
    private Long productId;
    private BigDecimal price;
    private int quantity;

    public BigDecimal getTotal() {
        return this.price.multiply(new BigDecimal(this.quantity));
    }
}
