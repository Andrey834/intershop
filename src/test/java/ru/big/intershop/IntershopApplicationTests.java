package ru.big.intershop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.big.intershop.config.TestContainersConfiguration;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureWebTestClient
class IntershopApplicationTests extends TestContainersConfiguration {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void contextLoads() {
        IntershopApplication.main(new String[]{});
        webTestClient.get().uri("/actuator/health").exchange()
                .expectStatus().isOk();
    }
}
