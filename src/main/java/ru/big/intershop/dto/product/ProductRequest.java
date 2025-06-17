package ru.big.intershop.dto.product;

import lombok.Builder;
import org.springframework.http.codec.multipart.FilePart;

import java.math.BigDecimal;

@Builder
public record ProductRequest(
        String title,
        String description,
        BigDecimal price,
        FilePart image) {
}
