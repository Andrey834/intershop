package ru.big.intershop.inregration.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.big.intershop.config.TestContainersConfiguration;
import ru.big.intershop.model.Product;
import ru.big.intershop.reposioty.ProductRepository;
import ru.big.intershop.service.ImageService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureWebTestClient
class ProductAdminControllerTest extends TestContainersConfiguration {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ProductRepository productRepository;
    @MockitoBean
    private ImageService imageService;
    private final List<Product> products = new ArrayList<>();
    private MockMultipartFile mockMultipartFile;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll().block();
        products.clear();
        mockMultipartFile = new MockMultipartFile(
                "test.jpg",
                "test.jpg",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());
    }

    @Test
    void viewAdminPanel() {
        addProducts(2);
        Product product1 = products.get(0);
        Product product2 = products.get(1);

        webTestClient.get().uri("/admin").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .consumeWith(response -> {
                    String body = response.getResponseBody();

                    assertNotNull(body);
                    assertTrue(body.contains(product1.getTitle()));
                    assertTrue(body.contains(product1.getDescription()));
                    assertTrue(body.contains(product2.getTitle()));
                    assertTrue(body.contains(product2.getDescription()));
                });
    }

    @Test
    void addProduct() {
        List<Product> prods = productRepository.findAll().collectList().block();
        assertNotNull(prods);
        assertTrue(prods.isEmpty());

        Product product = Product.builder()
                .title("test title")
                .description("test description")
                .price(BigDecimal.TEN)
                .image("test image")
                .build();

        when(imageService.save(any())).thenReturn(Mono.just(product.getImage()));

        webTestClient.post().uri(
                        uriBuilder -> uriBuilder
                                .scheme("http")
                                .path("/admin/product")
                                .queryParam("title", product.getId())
                                .queryParam("description", product.getDescription())
                                .queryParam("price", product.getPrice())
                                .build()
                )
                .attribute("image", mockMultipartFile)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectBody().isEmpty();

        prods = productRepository.findAll().collectList().block();
        assertNotNull(prods);
        assertEquals(1, prods.size());
    }

    @Test
    void processEditProduct() {
        addProducts(1);
        final Product product = products.getFirst();

        Product updateProduct = Product.builder()
                .title("test title")
                .description("test description")
                .price(BigDecimal.valueOf(10))
                .image("test image")
                .build();

        assertNotEquals(product.getTitle(), updateProduct.getTitle());
        assertNotEquals(product.getDescription(), updateProduct.getDescription());
        assertNotEquals(product.getPrice(), updateProduct.getPrice());

        when(imageService.save(any())).thenReturn(Mono.just(updateProduct.getImage()));

        webTestClient.post().uri(
                        uriBuilder -> uriBuilder
                                .scheme("http")
                                .path("/admin/product/" + product.getId() + "/edit")
                                .queryParam("title", updateProduct.getTitle())
                                .queryParam("description", updateProduct.getDescription())
                                .queryParam("price", updateProduct.getPrice())
                                .build()
                )
                .attribute("image", mockMultipartFile)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectBody().isEmpty();

        Product actualProduct = productRepository.findById(product.getId()).block();
        assertNotNull(actualProduct);
        assertEquals(updateProduct.getTitle(), actualProduct.getTitle());
        assertEquals(updateProduct.getDescription(), actualProduct.getDescription());
        assertEquals(0, updateProduct.getPrice().compareTo(actualProduct.getPrice()));


    }

    @Test
    void deleteProduct() {
        addProducts(1);
        final Product product = products.getFirst();
        List<Product> prods = productRepository.findAll().collectList().block();
        assertNotNull(prods);
        assertEquals(1, prods.size());

        webTestClient.post().uri("/admin/product/" + product.getId() + "/delete").exchange()
                .expectStatus().is3xxRedirection();


        prods = productRepository.findAll().collectList().block();
        assertNotNull(prods);
        assertEquals(0, prods.size());
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
