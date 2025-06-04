package ru.big.intershop.dto.order;

import lombok.Builder;
import ru.big.intershop.dto.payment.PaymentDto;
import ru.big.intershop.enums.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDto(
        Long id,
        LocalDateTime created,
        PaymentDto payment,
        List<OrderPartDto> parts
) {
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderPartDto part : parts) {
            total = total.add(part.product().price().multiply(new BigDecimal(part.quantity())));
        }
        return total;
    }

    public String getShortDate() {
        return created.format(DateTimeFormat.FOR_VIEW_SHORT.getFormatter());
    }
}
