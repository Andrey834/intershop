package ru.big.intershop.dto.cart;

import java.math.BigDecimal;
import java.util.List;

public record Cart(
        List<ItemCart> items
) {
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemCart item : items) {
            total = total.add(item.product().price().multiply(new BigDecimal(item.quantity())));
        }
        return total;
    }
}
