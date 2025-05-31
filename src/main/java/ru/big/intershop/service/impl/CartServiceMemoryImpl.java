package ru.big.intershop.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.big.intershop.dto.product.ProductDto;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.model.Cart;
import ru.big.intershop.model.OrderPart;
import ru.big.intershop.model.Product;
import ru.big.intershop.service.CartService;
import ru.big.intershop.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Service(value = "cartServiceMemoryImpl")
public class CartServiceMemoryImpl implements CartService {
    private static List<OrderPart> parts = new ArrayList<>();
    private final ProductService productService;

    public CartServiceMemoryImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void add(Long productId) {
        ProductDto productDto = productService.get(productId);
        parts.forEach(System.out::println);
    }

    @Override
    public void remove(String sessionId, Long id) {

    }

    @Override
    public void removeAll(String sessionId) {

    }

    @Override
    public List<ProductShortDto> getAll(String sessionId) {
        return List.of();
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void scheduled() {
        /*carts.keySet().forEach(key -> {
            Cart cart = carts.get(key);

        });*/
    }
}
