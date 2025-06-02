package ru.big.intershop.dto.product;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Builder
public record ProductRequest(
        String title,
        String description,
        BigDecimal price,
        MultipartFile image
) {
}
