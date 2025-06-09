package ru.big.intershop.mapper;

import ru.big.intershop.dto.product.ProductDto;
import ru.big.intershop.dto.product.ProductRequest;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.model.Product;

public class ProductMapper {

    private ProductMapper() {
    }

    public static Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .title(productRequest.title())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
    }

    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .created(product.getCreated().toString())
                .build();
    }

    public static ProductShortDto toShortDto(Product product) {
        return ProductShortDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .build();
    }
}
