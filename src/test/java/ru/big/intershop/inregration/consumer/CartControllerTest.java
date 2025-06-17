package ru.big.intershop.inregration.consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.big.intershop.config.TestContainersConfiguration;
import ru.big.intershop.dto.cart.ItemCartShort;
import ru.big.intershop.model.Product;
import ru.big.intershop.reposioty.ProductRepository;
import ru.big.intershop.service.CartService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureWebTestClient
class CartControllerTest extends TestContainersConfiguration {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductRepository productRepository;
    private final List<Product> products = new ArrayList<>();

    @BeforeEach
    void setUp() {
        products.clear();
        productRepository.deleteAll().block();
        addProducts();
    }

    @Test
    @DisplayName("Просмотр корзины")
    void viewCart() {
        var cart = cartService.getCart().block();
        assertNotNull(cart);
        assertTrue(cart.items().isEmpty());

        Product product = products.getFirst();

        ItemCartShort itemCartShort = ItemCartShort.builder()
                .productId(product.getId())
                .quantity(5)
                .from("product")
                .build();

        cartService.update(itemCartShort).block();

        webTestClient.get().uri("/cart").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();

                    assertNotNull(body);
                    assertTrue(body.contains(product.getTitle()));
                    assertTrue(body.contains(product.getDescription()));
                });
    }

    @Test
    @DisplayName("Добавить Item в корзину")
    void update() {
        var cart = cartService.getCart().block();
        assertNotNull(cart);
        assertTrue(cart.items().isEmpty());

        Product product = products.getFirst();
        int expectedQuantity = 5;

        webTestClient.post().uri(
                        uriBuilder -> uriBuilder
                                .scheme("http")
                                .path("/cart")
                                .queryParam("productId", product.getId())
                                .queryParam("from", "product")
                                .queryParam("quantity", expectedQuantity)
                                .build()
                )
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectBody().isEmpty();

        cart = cartService.getCart().block();
        assertNotNull(cart);
        assertEquals(1, cart.items().size());

        var items = cart.items().getFirst();
        assertEquals(product.getId(), items.product().id());
        assertEquals(product.getTitle(), items.product().title());
        assertEquals(expectedQuantity, items.quantity());
    }

    private void addProducts() {
        for (int i = 0; i < 10; i++) {
            Product product = Product.builder()
                    .title("Product " + i)
                    .description("Product " + i)
                    .image("Product " + i)
                    .created(LocalDateTime.now())
                    .price(BigDecimal.valueOf(Math.random()))
                    .build();
            productRepository.save(product).doOnNext(products::add).block();
        }
    }
}
