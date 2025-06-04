package ru.big.intershop.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.big.intershop.dto.ItemCart;
import ru.big.intershop.dto.ItemCartRequest;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.service.CartService;
import ru.big.intershop.service.ProductSearchService;
import ru.big.intershop.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "cartServiceMemoryImpl")
public class CartServiceMemoryImpl implements CartService {
    private static final Map<Long, Integer> cart = new HashMap<>();
    private final ProductService productService;
    private final ProductSearchService productSearchService;

    public CartServiceMemoryImpl(ProductService productService, ProductSearchService productSearchService) {
        this.productService = productService;
        this.productSearchService = productSearchService;
    }


    @Override
    public void update(ItemCartRequest itemCartRequest) {
        if (productService.existById(itemCartRequest.productId())) {
            if (itemCartRequest.quantity() == 0) {
                remove(itemCartRequest.productId());
            } else {
                cart.put(itemCartRequest.productId(), itemCartRequest.quantity());
            }
        }
    }

    @Override
    public void remove(Long productId) {
        cart.remove(productId);
    }

    @Override
    public void removeAll() {
        cart.clear();
    }

    @Override
    public Map<Long, Integer> getAll() {
        return cart;
    }

    @Override
    public List<ItemCart> getCart() {
        List<Long> cartIds = new ArrayList<>(cart.keySet());
        List<ProductShortDto> products = productSearchService.getAllByIds(cartIds);
        List<ItemCart> itemCarts = new ArrayList<>();

        products.forEach(product ->
                itemCarts.add(new ItemCart(product, cart.getOrDefault(product.id(), 0))));

        return itemCarts;
    }

    @Override
    public BigDecimal getTotal() {
        List<ItemCart> itemCarts = getCart();
        BigDecimal total = new BigDecimal(0);
        for (ItemCart itemCart : itemCarts) {
            total = total.add(itemCart.product().price().multiply(new BigDecimal(itemCart.quantity())));
        }
        return total;
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void scheduled() {
        cart.clear();
    }
}
