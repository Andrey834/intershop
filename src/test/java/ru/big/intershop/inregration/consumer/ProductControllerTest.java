package ru.big.intershop.inregration.consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.big.intershop.config.TestContainersConfiguration;
import ru.big.intershop.model.Product;
import ru.big.intershop.reposioty.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureWebTestClient
class ProductControllerTest extends TestContainersConfiguration {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ProductRepository productRepository;
    private final List<Product> products = new ArrayList<>();

    @BeforeEach
    void setUp() {
        productRepository.deleteAll().block();
    }

    @Test
    void viewProducts() {
        addProducts(2);
        Product product1 = products.get(0);
        Product product2 = products.get(1);
        webTestClient.get().uri("/product").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();

                    assertNotNull(body);
                    assertTrue(body.contains(product1.getTitle()));
                    assertTrue(body.contains(product1.getDescription()));
                    assertTrue(body.contains(product2.getTitle()));
                    assertTrue(body.contains(product2.getDescription()));
                });
    }

    @Test
    void viewProduct() {
        addProducts(1);
        Product product = products.getFirst();
        webTestClient.get().uri("/product/" + product.getId()).exchange()
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
    void viewProduct_whenInvalidId_thenNotFound() {
        long fakeId = Long.MAX_VALUE;
        webTestClient.get().uri("/product/" + fakeId).exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();
                    assertNotNull(body);
                    assertTrue(body.contains("Product not found with id: " + fakeId));
                });
    }

    private void addProducts(int quantity) {
        for (int i = 0; i < quantity; i++) {
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
