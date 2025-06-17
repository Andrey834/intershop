package ru.big.intershop.dto.order;

import lombok.Builder;
import ru.big.intershop.enums.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
public record OrderDto(
        Long id,
        LocalDateTime created,
        Long paymentId,
        List<OrderPartDto> parts
) {

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderPartDto part : parts) {
            total = total.add(part.product().price().multiply(new BigDecimal(part.quantity())));
        }
        return total;
    }

    public String getDate() {
        return created.format(DateTimeFormat.FOR_VIEW_SHORT.getFormatter());
    }

    public String getDateTime() {
        return created.format(DateTimeFormat.FOR_VIEW_FULL.getFormatter());
    }
}
